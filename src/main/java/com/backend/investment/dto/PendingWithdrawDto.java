package com.backend.investment.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PendingWithdrawDto {

    private Long id;

    private Long userId;

    private String phone;

    private BigDecimal requestedAmountUsdt;

    private BigDecimal requestedAmountInr;

    private BigDecimal serviceFee;

    private BigDecimal payableAmount;

    private String currency;

    private String withdrawType;

    private String paymentMethod;

    private String accountDetails;

    private String status;
    private LocalDateTime createdOn;

}