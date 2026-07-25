package com.backend.investment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.investment.entity.UserInvestment;

public interface UserInvestmentRepository extends JpaRepository<UserInvestment,Long>{

    List<UserInvestment> findByUserId(Long userId);
    List<UserInvestment> findByStatus(String status);

}
