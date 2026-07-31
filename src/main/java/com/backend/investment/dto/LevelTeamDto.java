package com.backend.investment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LevelTeamDto {

    private long totalMembers;

    private long activeMembers;

    private BigDecimal totalRecharge;

    private BigDecimal totalInvestment;

    private BigDecimal commission;

    private BigDecimal commissionRate;
}