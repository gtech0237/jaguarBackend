package com.backend.investment.service;

import com.backend.investment.dto.RechargeRequestDto;
import com.backend.investment.dto.RechargeResponseDto;

import java.util.List;

public interface IRechargeService {



    List<RechargeResponseDto> rechargeHistory(Long userId);

}