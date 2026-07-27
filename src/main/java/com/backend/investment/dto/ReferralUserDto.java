package com.backend.investment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReferralUserDto {

    private Long id;

    private String phone;

    private BigDecimal balance;

    private BigDecimal totalRecharge;

    private BigDecimal totalIncome;

    private String status;

}