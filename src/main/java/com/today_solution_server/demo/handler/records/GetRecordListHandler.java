package com.today_solution_server.demo.handler.records;

import com.today_solution_server.demo.dto.records.GetRecordListResponseDto;
import com.today_solution_server.demo.entity.Records;
import com.today_solution_server.demo.entity.Users;
import com.today_solution_server.demo.repository.RecordsRepository;
import com.today_solution_server.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
@Slf4j
@RequiredArgsConstructor
public class GetRecordListHandler {

    private final UsersRepository usersRepository;

    private final RecordsRepository recordsRepository;

    public GetRecordListResponseDto getRecordList(Integer userId) throws Exception{
        Users findUser = usersRepository.findOneByUserId(userId)
                .orElseThrow(() -> new NullPointerException("There isn't Entity"));

        List<Records> records = recordsRepository.findAllByUserOrderByRecordedDateDesc(findUser);

        List<GetRecordListResponseDto.RecordWrapper> recordWrapperList = new ArrayList<>();

        for (Records r : records) {
            String todaySolution = r.getTodaySolution() != null ? r.getTodaySolution() : null;

            String tmpRecordedDate = String.valueOf(r.getRecordedDate());
            String finalRecordedDate = tmpRecordedDate.replace("T", " ");
            log.info("finalRecordedDate={}", finalRecordedDate);



            GetRecordListResponseDto.RecordWrapper rw = GetRecordListResponseDto.RecordWrapper
                    .builder()
                    .recordId(String.valueOf(r.getRecordId()))
                    .recordTitle(r.getRecordTitle())
                    .recordContent(r.getRecordContent())
                    .todaySolution(todaySolution)
                    .recordedDate(finalRecordedDate)
                    .build();

            recordWrapperList.add(rw);
        }

        GetRecordListResponseDto res = GetRecordListResponseDto.builder()
                .recordList(recordWrapperList)
                .build();


        return res;
    }
}
