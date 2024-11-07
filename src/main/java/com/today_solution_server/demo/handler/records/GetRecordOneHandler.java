package com.today_solution_server.demo.handler.records;

import com.today_solution_server.demo.dto.records.GetRecordOneResponseDto;
import com.today_solution_server.demo.entity.Records;
import com.today_solution_server.demo.entity.Users;
import com.today_solution_server.demo.repository.RecordsRepository;
import com.today_solution_server.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GetRecordOneHandler {

    private final UsersRepository usersRepository;
    private final RecordsRepository recordsRepository;


    public GetRecordOneResponseDto getRecordOne(Integer recordId, Integer userId) throws Exception{
        Users findUser = usersRepository.findOneByUserId(userId).orElseThrow(() -> new NullPointerException("There isn't Entity"));
        Records findOneRecord = recordsRepository.findOneByUserAndRecordId(findUser, recordId).orElseThrow(() -> new NullPointerException("There isn't Entity"));

        String resRecordId = String.valueOf(findOneRecord.getRecordId());
        String recordTitle = findOneRecord.getRecordTitle();
        String recordContent = findOneRecord.getRecordContent();
        String todaySolution = findOneRecord.getTodaySolution() != null ? findOneRecord.getTodaySolution() : null;
        String recordedDate = String.valueOf(findOneRecord.getRecordedDate());

        GetRecordOneResponseDto res = GetRecordOneResponseDto.builder()
                .recordContent(recordContent)
                .recordTitle(recordTitle)
                .recordedDate(recordedDate)
                .todaySolution(todaySolution)
                .recordId(resRecordId).build();
        return res;
    }
}
