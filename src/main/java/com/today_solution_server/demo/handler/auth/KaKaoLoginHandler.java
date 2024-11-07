package com.today_solution_server.demo.handler.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.today_solution_server.demo.dto.auth.KaKaoLoginRequestDto;
import com.today_solution_server.demo.dto.auth.KaKaoLoginResponseDto;
import com.today_solution_server.demo.dto.user.GetUserResponseDto;
import com.today_solution_server.demo.entity.Users;
import com.today_solution_server.demo.handler.user.GetUserHandler;
import com.today_solution_server.demo.jwt.JwtUtils;
import com.today_solution_server.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class KaKaoLoginHandler {

    @Value("${kakao.clientId}")
    private String clientId;

    @Value("${kakao.redirectUri}")
    private String redirectUri;

    private String grantType = "authorization_code";

    private final GetUserHandler getUserHandler;
    private final UsersRepository usersRepository;
    private final JwtUtils jwtUtils;
    private final RedisTemplate<String, Object> redisTemplate;

    public KaKaoLoginResponseDto kakaoLogin(KaKaoLoginRequestDto reqBody) throws Exception {

        KaKaoLoginResponseDto res = new KaKaoLoginResponseDto();

        Map<String, String> mapForString = new HashMap<>();
        mapForString.put("client_id", clientId);
        mapForString.put("redirect_uri", redirectUri);
        mapForString.put("code", reqBody.getCode());
        mapForString.put("grant_type", grantType);

        String finalPostBody = getDataString(mapForString);

        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

        HttpResponse<String> response = client.send(HttpRequest.newBuilder(new URI(
                        "https://kauth.kakao.com/oauth/token"))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(finalPostBody)).build(), HttpResponse.BodyHandlers.ofString());


        JsonNode bodyJson = JsonMapper.builder().build().readTree(response.body());
        log.info("bodyJson={}", bodyJson);
        String accessToken = bodyJson.get("access_token").asText();

        GetUserResponseDto getUser = getUserHandler.getUser(accessToken);

        Optional<Users> optionalUser = usersRepository.findOneByNickName(getUser.getNickName());

        Users user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = Users.builder()
                    .nickName(getUser.getNickName())
                    .thumbnailImage(getUser.getThumbnailImage())
                    .profileImage(getUser.getProfileImage())
                    .build();
            user = usersRepository.save(user);
        }

        // token 생성
        String jwtToken = jwtUtils.generateToken(String.valueOf(user.getUserId()));
        log.info("jwtToken={}", jwtToken);

        // 로그아웃 구분하기 위해 redis 에 저장
        redisTemplate.opsForValue().set("JWT_TOKEN:" + user.getUserId(), jwtToken, jwtUtils.getExpiration(jwtToken));


        // 클라이언트에게 토큰, getUser 정보 반환
        res.setAccessToken(jwtToken);
        KaKaoLoginResponseDto.UserWrapper uw = KaKaoLoginResponseDto.UserWrapper.builder()
                .nickName(getUser.getNickName())
                .profileImage(getUser.getProfileImage())
                .thumbnailImage(getUser.getThumbnailImage())
                .build();

        res.setUserWrapper(uw);
        return res;
    }



    private String getDataString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }



}





