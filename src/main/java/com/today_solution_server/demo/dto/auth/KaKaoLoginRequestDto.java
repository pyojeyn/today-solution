package com.today_solution_server.demo.dto.auth;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@NoArgsConstructor
public class KaKaoLoginRequestDto {

    @NotBlank
    private String code;

}
