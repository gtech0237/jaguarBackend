package com.backend.investment.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.investment.Exception.BadRequestException;
import com.backend.investment.Exception.ResourceNotFoundException;
import com.backend.investment.dto.PlanRequestDto;
import com.backend.investment.dto.PlanResponseDto;
import com.backend.investment.entity.Plan;
import com.backend.investment.repository.PlanRepository;
import com.backend.investment.service.IPlanService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements IPlanService{

    private final PlanRepository planRepository;

    @Override
    public String createPlan(PlanRequestDto request){
        if(request.getPlanName() == null || request.getPlanName().isEmpty()){
            throw new BadRequestException("Plan Name is mandatory!");
        }
        if(request.getDescription() == null || request.getDescription().isEmpty()){
            throw new BadRequestException("Plan Description is mandatory!");
        }

        Optional<Plan> planOptional = planRepository.findByPlanName(request.getPlanName());
        if (planOptional.isPresent()) {
            throw new BadRequestException("Plan already exists.");
        }
        Plan plan = new Plan();

        plan.setPlanName(request.getPlanName().trim());
        plan.setDescription(request.getDescription().trim());
        plan.setStatus(request.getStatus() == null || request.getStatus().isBlank() ? "ACTIVE" : request.getStatus().trim());
        plan.setCreatedOn(LocalDateTime.now());

         planRepository.save(plan);

    return "Plan created seccessfully.";

    }

    @Override
    public List<PlanResponseDto> getAllPlans(){
        List<Plan> plansList = planRepository.findAll();
        return plansList.stream().map(plans ->{
            PlanResponseDto dto = new PlanResponseDto();
            dto.setId(plans.getId());
            dto.setPlanName(plans.getPlanName());
            dto.setDescription(plans.getDescription());
            dto.setStatus(plans.getStatus());
            return dto;
        }).collect(Collectors.toList()); 


    }

    @Override
    public PlanResponseDto getPlan(Long id) {
        Plan plan = planRepository.findById(id)
               .orElseThrow(() ->
                        new ResourceNotFoundException("Plan", "id", id.toString())
                );
        PlanResponseDto dto = new PlanResponseDto();
        dto.setId(plan.getId());
        dto.setPlanName(plan.getPlanName());
        dto.setDescription(plan.getDescription());
        dto.setStatus(plan.getStatus());
        return dto;
    }



}
