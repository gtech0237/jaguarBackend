package com.backend.investment.service.impl;

import com.backend.investment.Exception.ResourceNotFoundException;
import com.backend.investment.dto.BankAccountDto;
import com.backend.investment.dto.UserResponseDto;
import com.backend.investment.entity.User;
import com.backend.investment.mapper.UserMapper;
import com.backend.investment.repository.UserRepository;
import com.backend.investment.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto getLoggedInUser(String phone) {

        User user = userRepository.findByPhone(phone)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "phone",
                                phone
                        )
                );

        return UserMapper.userToUserResponseDto(user);
    }
    @Override
    public UserResponseDto saveBankAccount(String phone,
                                           BankAccountDto dto) {

        User user = userRepository.findByPhone(phone)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "phone",
                                phone
                        ));

        user.setAccountHolderName(dto.getAccountHolderName());

        user.setBankName(dto.getBankName());

        user.setAccountNumber(dto.getAccountNumber());

        user.setIfscCode(dto.getIfscCode());

        userRepository.save(user);

        return UserMapper.userToUserResponseDto(user);

    }
}