package com.backend.investment.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.investment.Exception.BadRequestException;
import com.backend.investment.Exception.ResourceNotFoundException;
import com.backend.investment.dto.RechargeRequestDto;
import com.backend.investment.dto.RechargeResponseDto;
import com.backend.investment.entity.RechargeHistory;
import com.backend.investment.entity.User;
import com.backend.investment.entity.WalletTransaction;
import com.backend.investment.repository.RechargeRepository;
import com.backend.investment.repository.UserRepository;
import com.backend.investment.repository.WalletTransactionRepository;
import com.backend.investment.service.IRechargeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RechargeServiceImpl implements IRechargeService {

    private final RechargeRepository rechargeRepository;

    @Override
    public List<RechargeResponseDto> rechargeHistory(Long userId) {

        return rechargeRepository
                .findByUserIdAndStatusOrderByCreatedOnDesc(
                        userId,
                        "SUCCESS"
                )
                .stream()
                .map(recharge -> {

                    RechargeResponseDto dto = new RechargeResponseDto();

                    dto.setId(recharge.getId());
                    dto.setUserId(recharge.getUser().getId());
                    dto.setAmount(recharge.getAmount());
                    dto.setPaymentMethod(recharge.getPaymentMethod());
                    dto.setTransactionId(recharge.getTransactionId());
                    dto.setStatus(recharge.getStatus());
                    dto.setCreatedOn(recharge.getCreatedOn());

                    return dto;
                })
                .toList();
    }

}