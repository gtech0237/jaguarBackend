package com.backend.investment.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.investment.entity.IncomeHistory;

public interface IncomeHistoryRepository extends JpaRepository<IncomeHistory,Long>{

    boolean existsByInvestmentIdAndIncomeDate(Long investmentId,LocalDate incomeDate);

}