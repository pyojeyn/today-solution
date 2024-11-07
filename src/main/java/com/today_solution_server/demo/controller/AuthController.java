package com.today_solution_server.demo.controller;

import com.today_solution_server.demo.dto.auth.KaKaoLoginRequestDto;
import com.today_solution_server.demo.dto.auth.KaKaoLoginResponseDto;
import com.today_solution_server.demo.handler.auth.KaKaoLoginHandler;
import com.today_solution_server.demo.handler.auth.LogoutHandler;
import com.today_solution_server.demo.jwt.JwtUtils;
import com.today_solution_server.demo.securityUser.CustomUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KaKaoLoginHandler kaKaoLoginHandler;
    private final LogoutHandler logoutHandler;

    @PostMapping ("login")
    public KaKaoLoginResponseDto kakaoLogin(@RequestBody KaKaoLoginRequestDto reqBody) throws Exception {
        return kaKaoLoginHandler.kakaoLogin(reqBody);
    }

    @PostMapping ("logout")
    public  Map<String, Object> logout(HttpServletRequest request, @AuthenticationPrincipal CustomUserDto user)throws Exception{
        Integer userId = Integer.parseInt(user.getUserId());
        return logoutHandler.logout(userId);
    }
}
