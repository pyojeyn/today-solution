package com.today_solution_server.demo.dto.records;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
@ToString
public class CreateRecordRequestDto {

    @NotBlank
    private String recordedDate; // 20241030

    @NotBlank
    private String recordContent;

    @NotBlank
    private String recordTitle;


    private String todaySolution;

}
