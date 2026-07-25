package com.backend.investment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanRequestDto {
    private String planName;

    private String description;

    private String status;

}
