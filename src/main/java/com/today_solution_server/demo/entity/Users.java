package com.today_solution_server.demo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@DynamicUpdate
@DynamicInsert
@Getter
@Setter
@Table
@Entity
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", columnDefinition = "INT UNSIGNED COMMENT 'PK'")
    private Integer userId;

    @Column(name = "nick_name", columnDefinition = "VARCHAR(50) COMMENT '카카오톡 닉네임'")
    private String nickName;

    @Column(name = "profile_image", columnDefinition = "VARCHAR(255) COMMENT '카카오톡 프로필 이미지 주소'")
    private String profileImage;

    @Column(name = "thumbnail_image", columnDefinition = "VARCHAR(255) COMMENT '카카오톡 프로필 썸네일 이미지 주소'")
    private String thumbnailImage;


    @Builder
    public Users(String nickName, String profileImage, String thumbnailImage) {
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.thumbnailImage = thumbnailImage;
    }
}
