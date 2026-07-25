package com.backend.investment.service.impl;

import com.backend.investment.Exception.DuplicateTransactionException;
import com.backend.investment.dto.PaymentRequestDto;
import com.backend.investment.entity.PaymentRequest;
import com.backend.investment.repository.PaymentRequestRepository;
import com.backend.investment.service.IPaymentRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentRequestServiceImpl implements IPaymentRequestService {

    private final PaymentRequestRepository paymentRequestRepository;

    @Override
    public String submitRecharge(PaymentRequestDto dto) {

        if (paymentRequestRepository.existsByTransactionId(dto.getTransactionId())) {
            throw new DuplicateTransactionException("This transaction hash has already been submitted.");
        }

        PaymentRequest request = new PaymentRequest();
        request.setUserId(dto.getUserId());
        request.setAmount(dto.getAmount());
        request.setCurrency("USDT");
        request.setTransactionId(dto.getTransactionId());
        request.setPaymentMethod(" BEP20");
        request.setStatus("PENDING");
        request.setCreatedOn(LocalDateTime.now());

        paymentRequestRepository.save(request);

        return "Recharge Request Submitted Successfully.";
    }
}