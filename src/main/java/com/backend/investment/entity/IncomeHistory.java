package com.backend.investment.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "income_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "investment_id")
    private UserInvestment investment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal incomeAmount;

    private LocalDate incomeDate;

    private String remarks;

    private LocalDateTime createdOn;

    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
    }

}