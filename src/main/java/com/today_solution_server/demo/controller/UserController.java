package com.today_solution_server.demo.controller;

import com.today_solution_server.demo.dto.user.GetUserRequestDto;
import com.today_solution_server.demo.dto.user.GetUserResponseDto;
import com.today_solution_server.demo.handler.user.GetUserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final GetUserHandler getUserHandler;

//    @GetMapping("")
//    public GetUserResponseDto getUser(HttpServletRequest request) throws Exception {
//
//        return getUserHandler.getUser(request);
//    }
}
