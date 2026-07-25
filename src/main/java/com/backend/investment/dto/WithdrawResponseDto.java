package com.backend.investment.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawResponseDto {

    private Long id;

    /**
     * Amount deducted from wallet (USDT)
     */
    private BigDecimal requestedAmountUsdt;

    /**
     * Equivalent INR
     */
    private BigDecimal requestedAmountInr;

    /**
     * Fee
     * USDT for USDT withdrawal
     * INR for Bank/UPI withdrawal
     */
    private BigDecimal serviceFee;

    /**
     * Final amount user receives
     */
    private BigDecimal payableAmount;

    /**
     * INR / USDT
     */
    private String currency;

    /**
     * ACCOUNT / MANUAL
     */
    private String withdrawType;

    /**
     * BANK / UPI / USDT
     */
    private String paymentMethod;

    /**
     * Wallet Address / UPI / Bank Details
     */
    private String accountDetails;

    /**
     * PENDING / APPROVED / REJECTED
     */
    private String status;

    private String remarks;

    private LocalDateTime createdOn;

    private String message;
}