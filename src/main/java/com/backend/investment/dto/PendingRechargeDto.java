package com.backend.investment.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PendingRechargeDto {

    private Long id;

    private Long userId;

    private String phone;

    private BigDecimal amount;

    private String transactionId;

    private String paymentMethod;

    private String status;

    private LocalDateTime createdOn;

}