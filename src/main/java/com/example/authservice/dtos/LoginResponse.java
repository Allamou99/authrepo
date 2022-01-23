package com.example.authservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
public class LoginResponse {
    private final String accesToken;
    private final String refreshToken;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final Date accesTokenExpirationDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final Date  refreshTokenExpirationDate;
    private final String username;
}
