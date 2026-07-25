package com.backend.investment.repository;

import com.backend.investment.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction,Long> {

    List<WalletTransaction> findByUserIdOrderByCreatedOnDesc(Long userId);

}