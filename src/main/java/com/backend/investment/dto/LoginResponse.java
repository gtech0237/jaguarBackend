package com.backend.investment.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;

    private UserResponseDto user;

}