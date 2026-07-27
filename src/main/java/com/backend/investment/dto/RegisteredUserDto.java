package com.backend.investment.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RegisteredUserDto {

    private Long id;

    private String phone;

    private String myReferralCode;

    private String referredByPhone;

    private Long referralCount;

    private BigDecimal balance;

    private BigDecimal totalIncome;

    private BigDecimal totalRecharge;

    private BigDecimal totalWithdraw;

    private String status;

    private String location;

    private String ipAddress;

    private LocalDateTime createdOn;

}