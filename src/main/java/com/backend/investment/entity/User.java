package com.backend.investment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
    private Long id;

    private String phone;

    private String password;

    @Column(name="withdraw_password")
    private String withdrawPassword;

    @Column(name = "my_referral_code", unique = true)
    private String myReferralCode;

    @Column(name = "referrer_id")
    private Long referrerId;

    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name="total_income")
    private BigDecimal totalIncome = BigDecimal.ZERO;

    @Column(name="total_recharge")
    private BigDecimal totalRecharge = BigDecimal.ZERO;

    @Column(name="total_withdraw")
    private BigDecimal totalWithdraw = BigDecimal.ZERO;

    private String status = "ACTIVE";

    @Column(name="ip_address")
    private String ip_address;

    @Column(name="location")
    private String location;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "bank_name")
    private String bankName;
    
    @Column(name="created_on")
    private LocalDateTime createdOn = LocalDateTime.now();
}
