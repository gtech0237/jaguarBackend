package com.backend.investment.controller;

import java.util.List;

import com.backend.investment.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.investment.dto.InvestmentRequestDto;
import com.backend.investment.dto.InvestmentResponseDto;
import com.backend.investment.service.InvestmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/investments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InvestmentController {

    private final InvestmentService investmentService;

    @PostMapping("/invest")
    public ResponseEntity<ApiResponse<InvestmentResponseDto>> invest(@Valid @RequestBody InvestmentRequestDto request) {

        InvestmentResponseDto dto = investmentService.invest(request);
        ApiResponse<InvestmentResponseDto> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Investment purchased successfully.");
        response.setData(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("investMentDetails/{userId}")
    public ResponseEntity<List<InvestmentResponseDto>> myInvestments(
            @PathVariable Long userId){

        return ResponseEntity.ok(
                investmentService.myInvestments(userId));

    }

}
