package com.today_solution_server.demo.jwt;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtUtils jwtUtils;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtUtils.resolveToken((HttpServletRequest) request);

        // 유효한 토큰인지 체크
        if (token != null && jwtUtils.validateToken(token)) {
            String key = "JWT_TOKEN:" + jwtUtils.extractUserIdFromToken(token);
            String storedToken = redisTemplate.opsForValue().get(key);

            //**로그인 여부 체크**
            if(redisTemplate.hasKey(key) && storedToken != null) {
                // 로그인 되어있을 때 !
                log.info("STATUS=LOGIN");
                Authentication authentication = jwtUtils.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                ((HttpServletRequest) request).setAttribute("error", "토큰이 유효하지 않음");
            }
        }

        chain.doFilter(request, response);
    }
}
