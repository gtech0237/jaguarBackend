package com.backend.investment.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyIncomeDetailsDto {

    private Long investmentId;

    private Long userId;

    private String phone;

    private String planName;

    private String productName;

    private BigDecimal investmentAmount;

    private BigDecimal dailyIncome;

    private Integer durationDays;

    private LocalDate purchaseDate;

    private LocalDate endDate;

    private Integer completedDays;

    private Integer daysRemaining;

    private BigDecimal availableBalance;

    private BigDecimal totalIncomePaid;

    private String status;

}