package com.today_solution_server.demo.handler.records;

import com.today_solution_server.demo.dto.records.CreateRecordRequestDto;
import com.today_solution_server.demo.entity.Records;
import com.today_solution_server.demo.entity.Users;
import com.today_solution_server.demo.repository.RecordsRepository;
import com.today_solution_server.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
@Transactional
@RequiredArgsConstructor
public class CreateRecordHandler {

    private final UsersRepository usersRepository;
    private final RecordsRepository recordsRepository;


    public Map<String, Object> create(CreateRecordRequestDto reqbody, Integer userId) throws Exception{

        Users findUser = usersRepository.findOneByUserId(userId)
                .orElseThrow(() -> new NullPointerException("There isn't Entity"));

        String todaySolution = null;
        String recordContent = reqbody.getRecordContent();
        String recordTitle = reqbody.getRecordTitle();
        if(!reqbody.getTodaySolution().equals("") && reqbody.getTodaySolution() != null){
            todaySolution = reqbody.getTodaySolution();
        }


        String recordDt = reqbody.getRecordedDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 20241030 16:33:00
        //
        LocalDateTime recordedDatetime = LocalDateTime.parse(recordDt, formatter);

        Records record = Records.builder()
                .recordContent(recordContent)
                .recordTitle(recordTitle)
                .recordedDate(recordedDatetime)
                .todaySolution(todaySolution)
                .user(findUser)
                .build();

        recordsRepository.save(record);

        Integer recordId = record.getRecordId();

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("recordId", recordId);

        return resMap;
    }
}
