package com.backend.investment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class InvestmentResponseDto {

    private Long investmentId;

    private Long userId;

    private Long productId;

    private String productName;

    private BigDecimal investmentAmount;

    private BigDecimal dailyIncome;

    private Integer durationDays;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

}
