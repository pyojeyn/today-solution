package com.today_solution_server.demo.dto.auth;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class KaKaoLoginResponseDto {

    private String accessToken;



    private UserWrapper userWrapper;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserWrapper{

        private String nickName;

        private String profileImage;

        private String thumbnailImage;

        @Builder
        public UserWrapper(String nickName, String profileImage, String thumbnailImage) {
            this.nickName = nickName;
            this.profileImage = profileImage;
            this.thumbnailImage = thumbnailImage;
        }
    }

    @Builder
    public KaKaoLoginResponseDto(String accessToken, UserWrapper userWrapper) {
        accessToken = accessToken;
        this.userWrapper = userWrapper;
    }
}
