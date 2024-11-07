package com.today_solution_server.demo.controller;

import com.today_solution_server.demo.dto.records.CreateRecordRequestDto;
import com.today_solution_server.demo.dto.records.GetRecordListResponseDto;
import com.today_solution_server.demo.dto.records.GetRecordOneResponseDto;
import com.today_solution_server.demo.dto.records.UpdateRecordRequestDto;
import com.today_solution_server.demo.handler.records.*;
import com.today_solution_server.demo.jwt.JwtUtils;
import com.today_solution_server.demo.securityUser.CustomUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
@Slf4j
public class RecordController {

    private final CreateRecordHandler createRecordHandler;
    private final GetRecordOneHandler getRecordOneHandler;
    private final GetRecordListHandler getRecordListHandler;
    private final UpdateRecordHandler updateRecordHandler;
    private final DeleteRecordHandler deleteRecordHandler;


    @PostMapping("")
    public Map<String, Object> createRecord(@RequestBody CreateRecordRequestDto reqbody, @AuthenticationPrincipal CustomUserDto user) throws Exception {
        Integer userId = Integer.parseInt(user.getUserId());
        return createRecordHandler.create(reqbody, userId);
    }



    @GetMapping("")
    public GetRecordOneResponseDto getRecordOne(@RequestParam Integer recordId, @AuthenticationPrincipal CustomUserDto user) throws Exception {
        Integer userId = Integer.parseInt(user.getUserId());
        return getRecordOneHandler.getRecordOne(recordId, userId);
    }

    @GetMapping("list")
    public GetRecordListResponseDto getRecordList(@AuthenticationPrincipal CustomUserDto user) throws Exception {
        Integer userId = Integer.parseInt(user.getUserId());
        return getRecordListHandler.getRecordList(userId);
    }

    @PatchMapping("")
    public Map<String, Object> updateRecord(@RequestBody UpdateRecordRequestDto req, @AuthenticationPrincipal CustomUserDto user) throws Exception {
        Integer userId = Integer.parseInt(user.getUserId());
        return updateRecordHandler.updateRecord(userId, req);
    }

    @DeleteMapping("")
    public Map<String, Object> deleteRecord(@RequestParam Integer recordId, @AuthenticationPrincipal CustomUserDto user) throws Exception{
        Integer userId = Integer.parseInt(user.getUserId());
        return deleteRecordHandler.deleteRecord(userId, recordId);
    }
}
