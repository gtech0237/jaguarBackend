package com.backend.investment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    private String transactionType;

    private BigDecimal amount;

    private BigDecimal openingBalance;

    private BigDecimal closingBalance;

    private Long referenceId;

    private String referenceType;

    private String remarks;

    private LocalDateTime createdOn;

    @PrePersist
    public void prePersist(){
        createdOn=LocalDateTime.now();
    }

}