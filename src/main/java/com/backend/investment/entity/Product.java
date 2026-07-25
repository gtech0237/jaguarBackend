package com.backend.investment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "investment_amount")
    private BigDecimal investmentAmount;

    @Column(name = "daily_income")
    private BigDecimal dailyIncome;

    @Column(name = "duration_days")
    private Integer durationDays;

    @Column(name = "image_url")
    private String imageUrl;

    private String status;

    @Column(name = "created_on")
    private LocalDateTime createdOn;


}
