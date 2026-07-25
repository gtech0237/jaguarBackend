package com.backend.investment.service;

import com.backend.investment.dto.WithdrawRequestDto;
import com.backend.investment.dto.WithdrawResponseDto;
import com.backend.investment.entity.WithdrawHistory;

import java.util.List;

public interface IWithdrawService {
    WithdrawResponseDto requestWithdraw(WithdrawRequestDto request);
    List<WithdrawResponseDto> withdrawHistory(Long userId);

}
