package com.backend.investment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(length = 10)
    private String currency = "USD";

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "screenshot_url")
    private String screenshotUrl;

    private String status = "PENDING";

    @Column(name = "admin_remarks")
    private String adminRemarks;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "verified_on")
    private LocalDateTime verifiedOn;

    @PrePersist
    public void prePersist() {
        this.createdOn = LocalDateTime.now();
    }
}