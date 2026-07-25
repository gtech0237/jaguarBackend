package com.backend.investment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_investments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInvestment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    private BigDecimal investmentAmount;

    private BigDecimal dailyIncome;

    private Integer durationDays;

    private BigDecimal totalIncomeGenerated;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    private LocalDateTime createdOn;

    @PrePersist
    public void prePersist(){

        createdOn=LocalDateTime.now();

        if(status==null)
            status="ACTIVE";

        if(totalIncomeGenerated==null)
            totalIncomeGenerated=BigDecimal.ZERO;

    }

}