package com.backend.investment.service;

import com.backend.investment.dto.BankAccountDto;
import com.backend.investment.dto.TeamResponseDto;
import com.backend.investment.dto.UserResponseDto;
import com.backend.investment.entity.User;

import java.math.BigDecimal;

public interface IUserService {

    UserResponseDto getLoggedInUser(String phone);
    UserResponseDto saveBankAccount(String phone, BankAccountDto dto);
    TeamResponseDto getMyTeam(String phone);
    void distributeReferralCommission(User investedUser, BigDecimal investmentAmountInr
    );
}