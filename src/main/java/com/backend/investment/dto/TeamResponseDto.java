package com.backend.investment.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TeamResponseDto {

    private long totalMembers;

    private long activeMembers;

    private BigDecimal totalRecharge;

    private BigDecimal totalInvestment;

    private BigDecimal commission;

    private LevelTeamDto level1;

    private LevelTeamDto level2;

    private LevelTeamDto level3;

    private List<ReferralUserDto> members;
}