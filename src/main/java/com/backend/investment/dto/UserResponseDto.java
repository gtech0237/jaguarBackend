package com.backend.investment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String phone;
    private String referralCode;
    private BigDecimal balance;
    private BigDecimal totalIncome;
    private BigDecimal totalRecharge;
    private BigDecimal totalWithdraw;
    private String status;
    private String location;
    private String ipAddress;
    private String accountHolderName;

    private String accountNumber;

    private String ifscCode;

    private String bankName;
    private LocalDateTime createdOn;
}