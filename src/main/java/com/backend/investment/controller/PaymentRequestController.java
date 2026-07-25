package com.backend.investment.controller;

import com.backend.investment.dto.PaymentRequestDto;
import com.backend.investment.service.IPaymentRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentRequestController {

    private final IPaymentRequestService paymentRequestService;
    @PostMapping("/request")
    public ResponseEntity<String> requestRecharge(
            @RequestBody PaymentRequestDto dto){

        return ResponseEntity.ok(
                paymentRequestService.submitRecharge(dto)
        );

    }
}
