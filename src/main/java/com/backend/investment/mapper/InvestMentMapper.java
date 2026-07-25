package com.backend.investment.mapper;

import com.backend.investment.dto.InvestmentResponseDto;
import com.backend.investment.entity.UserInvestment;

public class InvestMentMapper {

    private InvestMentMapper() {
    }

    public static InvestmentResponseDto mapToInvestMentDto(UserInvestment investment) {

        InvestmentResponseDto dto = new InvestmentResponseDto();

        dto.setInvestmentId(investment.getId());
        dto.setUserId(investment.getUser().getId());
        dto.setProductId(investment.getProduct().getId());
        dto.setProductName(investment.getProduct().getProductName());
        dto.setInvestmentAmount(investment.getInvestmentAmount());
        dto.setDailyIncome(investment.getDailyIncome());
        dto.setDurationDays(investment.getDurationDays());
        dto.setStartDate(investment.getStartDate());
        dto.setEndDate(investment.getEndDate());
        dto.setStatus(investment.getStatus());

        return dto;
    }
}
