package com.backend.investment.service;

import java.util.List;

import com.backend.investment.dto.InvestmentRequestDto;
import com.backend.investment.dto.InvestmentResponseDto;

public interface InvestmentService {
    
    InvestmentResponseDto invest(InvestmentRequestDto request);

    List<InvestmentResponseDto> myInvestments(Long userId);

}
