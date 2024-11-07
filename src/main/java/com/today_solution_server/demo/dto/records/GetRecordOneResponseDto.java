package com.today_solution_server.demo.dto.records;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GetRecordOneResponseDto {

    private String recordId;

    private String recordedDate;

    private String recordContent;

    private String recordTitle;

    private String todaySolution;


    @Builder
    public GetRecordOneResponseDto(String recordId, String recordedDate, String recordContent, String recordTitle, String todaySolution) {
        this.recordId = recordId;
        this.recordedDate = recordedDate;
        this.recordContent = recordContent;
        this.recordTitle = recordTitle;
        this.todaySolution = todaySolution;
    }
}
