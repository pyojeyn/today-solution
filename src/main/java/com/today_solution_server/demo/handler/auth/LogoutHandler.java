package com.today_solution_server.demo.handler.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class LogoutHandler {

    private final RedisTemplate<String, Object> redisTemplate;

    public Map<String, Object> logout(Integer userId) throws Exception {

        //Token 에서 로그인한 사용자 정보 추출후  로그아웃 처리
        if (redisTemplate.opsForValue().get("JWT_TOKEN:" + userId) != null) {
            redisTemplate.delete("JWT_TOKEN:" + userId );
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("id", userId);
        resMap.put("code", 200);

        return resMap;
    }
}
