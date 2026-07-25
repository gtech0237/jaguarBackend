package com.backend.investment.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawRequestDto {

    private Long userId;

    /**
     * Amount entered by user
     * INR if Bank withdrawal
     * USDT if Crypto withdrawal
     */
    private BigDecimal amount;

    // ACCOUNT / MANUAL
    private String withdrawType;

    // BANK / UPI / USDT
    private String paymentMethod;

    // Wallet Address / Bank Details
    private String accountDetails;

}