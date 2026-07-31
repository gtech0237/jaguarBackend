package com.backend.investment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReferralUserDto {

    private Long id;

    private String phone;

    private BigDecimal balance;

    private BigDecimal totalIncome;

    private BigDecimal totalRecharge;

    private BigDecimal totalInvestment;

    private BigDecimal commission;

    private Integer level;

    private String status;

    private String createdOn;
}