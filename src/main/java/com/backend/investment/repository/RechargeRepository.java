package com.backend.investment.repository;

import com.backend.investment.entity.RechargeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RechargeRepository extends JpaRepository<RechargeHistory,Long> {

    List<RechargeHistory> findByUserId(Long userId);
    List<RechargeHistory> findByUserIdAndStatusOrderByCreatedOnDesc(
            Long userId,
            String status
    );

}