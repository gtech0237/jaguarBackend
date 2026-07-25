package com.backend.investment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InvestmentRequestDto {
    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

}