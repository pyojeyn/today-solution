package com.today_solution_server.demo.dto.user;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GetUserResponseDto {

    private String nickName;

    private String profileImage;

    private String thumbnailImage;

    @Builder
    public GetUserResponseDto(String nickName, String profileImage, String thumbnailImage) {
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.thumbnailImage = thumbnailImage;
    }
}
