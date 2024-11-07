package com.today_solution_server.demo.jwt;

import com.today_solution_server.demo.securityUser.CustomUserDto;
import com.today_solution_server.demo.securityUser.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@NoArgsConstructor
public class JwtUtils {

    private UserService userService;

    @Autowired
    public JwtUtils(UserService userService) {
        this.userService = userService;
    }

    private static final String ACCESS_TOKEN_SECRET_KEY = "c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppd29vbi1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHVjennyispreety";

    final static long AccessTokenExpiresIn = 60L * 60 * 24 * 1000; // 1 days
    //final static long AccessTokenExpiresIn = 1000L; // ACCESS 토큰 만료 검사 테스트 시. 1초
    final static long RefreshTokenExpiresIn = 60L * 60 * 24 * 1000 * 30; // 30 days
    //final static long RefreshTokenExpiresIn = 1000L; // REFRESH 토큰 만료 검사 테스트 시. 1초

    public String generateToken(String userId){
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + AccessTokenExpiresIn);

        byte[] keyBytes = Decoders.BASE64.decode(ACCESS_TOKEN_SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);


        String accessToken = Jwts.builder()
                .setSubject(userId)       // payload "sub": "name"
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                .compact();

        log.info("JwtAccessToken={}", accessToken);

        return accessToken;
    }

    public String extractUserIdFromToken(String token) {
        byte[] keyBytes = Decoders.BASE64.decode(ACCESS_TOKEN_SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.parserBuilder()
                .setSigningKey(key) // JWT 생성에 사용했던 키를 지정
                .build()
                .parseClaimsJws(token.replace("Bearer ", "")) // "Bearer " 제거 후 토큰 파싱
                .getBody()
                .getSubject(); // subject에 담긴 userId 추출
    }


    // 유효기간까지 남은 시간 구하기
    public long getExpiration(String token) {
        byte[] keyBytes = Decoders.BASE64.decode(ACCESS_TOKEN_SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Date expiration = claims.getExpiration();
        long now = System.currentTimeMillis();

        return (expiration.getTime() - now) / 1000;  // 초 단위 반환
    }


    // token 추출하기
    public String resolveToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // "Bearer "를 제외한 토큰 반환
        }

        return null; // Authorization 헤더가 없거나 Bearer가 아닐 경우 null 반환
    }


    //토큰 유효성, 만료일자 확인
    public boolean validateToken(String token) {
        byte[] keyBytes = Decoders.BASE64.decode(ACCESS_TOKEN_SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.debug(e.getMessage());
            return false;
        }
    }

    //인증 정보 조회
    public Authentication getAuthentication(String token) {
        //Spring Security에서 제공하는 메서드 override해서 사용해야 함
        CustomUserDto customUserDto = userService.loadUserByUsername(this.extractUserIdFromToken(token));
        return new UsernamePasswordAuthenticationToken(customUserDto, "", customUserDto.getAuthorities());
    }


}
