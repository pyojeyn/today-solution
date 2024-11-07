package com.today_solution_server.demo.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class GetUserRequestDto {

    private String accessToken;
}
