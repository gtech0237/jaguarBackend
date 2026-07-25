package com.backend.investment.service;

import com.backend.investment.dto.PaymentRequestDto;
import com.backend.investment.entity.PaymentRequest;

public interface IPaymentRequestService {
    String submitRecharge(PaymentRequestDto dto);
}
