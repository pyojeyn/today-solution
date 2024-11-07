package com.today_solution_server.demo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicUpdate
@DynamicInsert
@Getter
@Setter
@Table
@Entity
@NoArgsConstructor
public class Records {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", columnDefinition = "INT UNSIGNED COMMENT 'PK'")
    private Integer recordId;

    @Column(name = "record_title", columnDefinition = "VARCHAR(100) COMMENT '제목'", nullable = false)
    private String recordTitle;

    @Column(name = "record_content", columnDefinition = "TEXT COMMENT '글내용'", nullable = false)
    private String recordContent;

    @Column(name = "today_solution", columnDefinition = "VARCHAR(100) COMMENT '오늘의 솔루션'")
    private String todaySolution;


    @Column(name = "recorded_date", columnDefinition = "TIMESTAMP(6) COMMENT '생성일'", nullable = false)
    private LocalDateTime recordedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "INT UNSIGNED COMMENT 'user_id FK'", nullable = false)
    private Users user;

    @Builder
    public Records(String recordTitle, String recordContent, String todaySolution, Users user,LocalDateTime recordedDate) {
        this.recordTitle = recordTitle;
        this.recordContent = recordContent;
        this.todaySolution = todaySolution;
        this.user = user;
        this.recordedDate = recordedDate;
    }
}
