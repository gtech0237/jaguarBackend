package com.backend.investment.service;

import com.backend.investment.dto.BankAccountDto;
import com.backend.investment.dto.UserResponseDto;

public interface IUserService {

    UserResponseDto getLoggedInUser(String phone);
    UserResponseDto saveBankAccount(String phone, BankAccountDto dto);

}