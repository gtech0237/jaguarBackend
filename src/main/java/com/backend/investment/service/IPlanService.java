package com.backend.investment.service;

import java.util.List;

import com.backend.investment.dto.PlanRequestDto;
import com.backend.investment.dto.PlanResponseDto;

public interface IPlanService {

    String createPlan(PlanRequestDto request);

    List<PlanResponseDto> getAllPlans();

    PlanResponseDto getPlan(Long id);


}
