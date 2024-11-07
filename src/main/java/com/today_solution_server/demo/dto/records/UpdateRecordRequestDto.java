package com.today_solution_server.demo.dto.records;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class UpdateRecordRequestDto {

    @NotBlank
    private String recordId;

    private String recordedDate;
//    private Boolean isRecordedDateDirty = false;

//    public void setRecordedDate(String recordedDate){
//        this.isRecordedDateDirty = true;
//        this.recordedDate = recordedDate;
//    }

    private String recordContent;
//    private Boolean isRecordContentDirty = false;
//    public void setRecordContent(String recordContent){
//        this.isRecordContentDirty = true;
//        this.recordContent = recordContent;
//    }

    private String recordTitle;
//    private Boolean isRecordTitleDirty = false;
//    public void setRecordTitle(String recordTitle){
//        this.isRecordTitleDirty = true;
//        this.recordTitle = recordTitle;
//    }

}
