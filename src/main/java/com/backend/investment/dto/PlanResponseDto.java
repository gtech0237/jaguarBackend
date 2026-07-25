package com.backend.investment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanResponseDto {
    private Long id;

    private String planName;

    private String description;

    private String status;

}
