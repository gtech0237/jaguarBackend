package com.backend.investment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.investment.dto.PlanRequestDto;
import com.backend.investment.dto.PlanResponseDto;
import com.backend.investment.service.IPlanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PlanController {

    private final IPlanService planService;


    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody PlanRequestDto request) {

        return ResponseEntity.ok(planService.createPlan(request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlanResponseDto>> getAll() {

        return ResponseEntity.ok(planService.getAllPlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDto> getById(@PathVariable Long id) {

        return ResponseEntity.ok(planService.getPlan(id));
    }


}
