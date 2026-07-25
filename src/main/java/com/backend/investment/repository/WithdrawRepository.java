package com.backend.investment.repository;

import com.backend.investment.entity.WithdrawHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawRepository extends JpaRepository<WithdrawHistory,Long> {

    List<WithdrawHistory> findByStatus(String status);
    List<WithdrawHistory> findByUserIdOrderByCreatedOnDesc(Long userId);

}
