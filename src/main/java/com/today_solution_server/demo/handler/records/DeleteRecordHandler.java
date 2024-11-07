package com.today_solution_server.demo.handler.records;

import com.today_solution_server.demo.entity.Records;
import com.today_solution_server.demo.entity.Users;
import com.today_solution_server.demo.repository.RecordsRepository;
import com.today_solution_server.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DeleteRecordHandler {

    private final UsersRepository usersRepository;
    private final RecordsRepository recordsRepository;

    public Map<String, Object> deleteRecord(Integer userId, Integer recordId) throws Exception{
        Users findUser = usersRepository.findOneByUserId(userId)
                .orElseThrow(() -> new NullPointerException("There isn't Entity"));

        Records deleteRecord = recordsRepository.findOneByUserAndRecordId(findUser, recordId)
                .orElseThrow(() -> new NullPointerException("There isn't Entity"));

        recordsRepository.delete(deleteRecord);

        Map<String, Object> res = new HashMap<>();

        res.put("code", 200);

        return res;
    }
}
