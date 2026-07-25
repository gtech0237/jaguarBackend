package com.backend.investment.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {

    private Long planId;

    private String productName;

    private BigDecimal investmentAmount;

    private BigDecimal dailyIncome;

    private Integer durationDays;

    private String imageUrl;

    private String status;
}
