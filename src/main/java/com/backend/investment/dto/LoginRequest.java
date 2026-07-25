package com.backend.investment.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String phone;
    private String password;

}
