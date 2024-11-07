package com.today_solution_server.demo.dto.records;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GetRecordListResponseDto {


    private List<RecordWrapper> recordList;


    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class RecordWrapper{
        private String recordId;
        private String recordedDate;
        private String recordContent;
        private String recordTitle;
        private String todaySolution;

        @Builder
        public RecordWrapper(String recordId, String recordedDate, String recordContent, String recordTitle, String todaySolution) {
            this.recordId = recordId;
            this.recordedDate = recordedDate;
            this.recordContent = recordContent;
            this.recordTitle = recordTitle;
            this.todaySolution = todaySolution;
        }
    }

    @Builder
    public GetRecordListResponseDto(List<RecordWrapper> recordList) {
        this.recordList = recordList;
    }
}
