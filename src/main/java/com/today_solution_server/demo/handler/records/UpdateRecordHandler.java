package com.today_solution_server.demo.handler.records;

import com.today_solution_server.demo.dto.records.UpdateRecordRequestDto;
import com.today_solution_server.demo.entity.Records;
import com.today_solution_server.demo.entity.Users;
import com.today_solution_server.demo.repository.RecordsRepository;
import com.today_solution_server.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UpdateRecordHandler {

    private final UsersRepository usersRepository;
    private final RecordsRepository recordsRepository;

    public Map<String, Object> updateRecord(Integer userId, UpdateRecordRequestDto req) throws Exception{

        Users findUser = usersRepository.findOneByUserId(userId)
                .orElseThrow(() -> new NullPointerException("There isn't entity"));

        Integer recordId = Integer.parseInt(req.getRecordId());

        Records record = recordsRepository.findOneByUserAndRecordId(findUser, recordId)
                .orElseThrow(() -> new NullPointerException("There isn't entity"));

        // 1. isDirty
//        if(req.getIsRecordContentDirty()){
//            record.setRecordContent(req.getRecordContent());
//        }
//
//        if(req.getIsRecordTitleDirty()){
//            record.setRecordTitle(req.getRecordTitle());
//        }
//
//        if(req.getIsRecordedDateDirty()){
//            String ld = req.getRecordedDate();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 20241030 16:33:00
//            LocalDateTime recordedDatetime = LocalDateTime.parse(ld, formatter);
//
//            record.setRecordedDate(recordedDatetime);
//        }


        //2. 기존 데이터와 비교비교
        if (!record.getRecordedDate().equals(req.getRecordedDate())){
            String ld = req.getRecordedDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime recordedDatetime = LocalDateTime.parse(ld, formatter);

            record.setRecordedDate(recordedDatetime);
        }

        if(!record.getRecordTitle().equals(req.getRecordTitle())){
            record.setRecordTitle(req.getRecordTitle());
        }

        if(!record.getRecordContent().equals(req.getRecordContent())){
            record.setRecordContent(req.getRecordContent());
        }
        recordsRepository.save(record);

        Map<String, Object> res = new HashMap<>();

        res.put("code", 200);

        return res;
    }



}
