package com.backend.investment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "withdraw_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Amount deducted from wallet (USDT)
     */
    @Column(name = "requested_amount_usdt", nullable = false, precision = 15, scale = 2)
    private BigDecimal requestedAmountUsdt;

    /**
     * Equivalent INR
     */
    @Column(name = "requested_amount_inr", nullable = false, precision = 15, scale = 2)
    private BigDecimal requestedAmountInr;

    /**
     * Service Fee
     * Stored in same currency as payable amount
     */
    @Column(name = "service_fee", nullable = false, precision = 15, scale = 2)
    private BigDecimal serviceFee;

    /**
     * Final amount to send
     * INR for Bank
     * USDT for Crypto
     */
    @Column(name = "payable_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal payableAmount;

    /**
     * ACCOUNT / MANUAL
     */
    @Column(name = "withdraw_type", nullable = false, length = 20)
    private String withdrawType;

    /**
     * BANK / UPI / USDT
     */
    @Column(name = "payment_method", length = 20)
    private String paymentMethod;

    /**
     * Bank details / Wallet address
     */
    @Column(name = "account_details", length = 500)
    private String accountDetails;

    /**
     * Currency user receives
     * INR / USDT
     */
    @Column(name = "currency", nullable = false, length = 10)
    private String currency;

    /**
     * PENDING / APPROVED / REJECTED
     */
    @Column(nullable = false, length = 20)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "approved_on")
    private LocalDateTime approvedOn;

    @PrePersist
    public void prePersist() {

        if (createdOn == null) {
            createdOn = LocalDateTime.now();
        }

        if (status == null) {
            status = "PENDING";
        }

    }

}