package com.backend.investment.repository;

import com.backend.investment.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest,Long> {
    List<PaymentRequest> findByStatus(String status);
    boolean existsByTransactionId(String transactionId);
}
