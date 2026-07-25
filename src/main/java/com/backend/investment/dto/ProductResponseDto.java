package com.backend.investment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {

    private Long id;    
    private Long planId;

    private String planName;

    private String productName;

    private BigDecimal investmentAmount;

    private BigDecimal dailyIncome;

    private Integer durationDays;

    private String imageUrl;

    private String status;

    private LocalDateTime createdOn;
}
