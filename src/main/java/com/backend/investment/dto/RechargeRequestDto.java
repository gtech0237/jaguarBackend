package com.backend.investment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RechargeRequestDto {

    private Long userId;

    private BigDecimal amount;

    private String paymentMethod;

    private String transactionId;
}