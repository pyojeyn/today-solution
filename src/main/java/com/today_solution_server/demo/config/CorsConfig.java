package com.today_solution_server.demo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowedOrigin}")
    private String allowedUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        log.info("CORS.allowedUrl={}", allowedUrl);

        registry.addMapping("/**")
                .allowedOrigins(allowedUrl) // 허용할 출처
                .allowedHeaders("*")  // 허용할 헤더
                .allowedMethods("GET", "POST", "PATCH", "DELETE") // 허용할 HTTP method
                .allowCredentials(true) // 쿠키 인증 요청 허용
                .maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
    }

}
