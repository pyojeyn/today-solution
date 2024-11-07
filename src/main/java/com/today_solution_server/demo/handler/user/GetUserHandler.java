package com.today_solution_server.demo.handler.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.today_solution_server.demo.dto.user.GetUserRequestDto;
import com.today_solution_server.demo.dto.user.GetUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class GetUserHandler {


    public GetUserResponseDto getUser(String accessToken) throws Exception {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
        log.info("accessToken", accessToken);
        HttpResponse<String> response = client.send(HttpRequest.newBuilder(new URI(
                                "https://kapi.kakao.com/v2/user/me"))
                        .timeout(Duration.ofSeconds(10))
                        .headers(
                                "Content-Type", "application/x-www-form-urlencoded;charset=utf-8",
                                "Authorization", "Bearer " + accessToken  // Bearer 토큰 추가
                        )
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString());

        JsonNode bodyJson = JsonMapper.builder().build().readTree(response.body());
        log.info("bodyJson={}", bodyJson);

        String nickName = bodyJson.get("properties").get("nickname").asText();
        String profileImage = bodyJson.get("properties").get("profile_image").asText();
        String thumbnailImage = bodyJson.get("properties").get("thumbnail_image").asText();

        GetUserResponseDto res = GetUserResponseDto.builder()
                .nickName(nickName).thumbnailImage(thumbnailImage).profileImage(profileImage).build();

        return res;
    }
}
