package com.backend.investment.controller;

import com.backend.investment.dto.*;
import com.backend.investment.entity.PaymentRequest;
import com.backend.investment.entity.WithdrawHistory;
import com.backend.investment.service.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminAuthController {
    private final IAdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(@RequestBody AdminLoginRequest request) {
        return ResponseEntity.ok(adminService.login(request)
        );
    }
    @GetMapping("/payments/pending")
    public ResponseEntity<List<PendingRechargeDto>> getPendingRechargeRequests() {

        return ResponseEntity.ok(
                adminService.getPendingRechargeRequests()
        );
    }
    @PostMapping("/payments/{id}/approve")
    public ResponseEntity<String> approve(@PathVariable Long id){
        return ResponseEntity.ok(adminService.approveRecharge(id)
        );
    }

    @PostMapping("/payments/{id}/reject")
    public ResponseEntity<String> reject(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.rejectRecharge(id));

    }

    @GetMapping("/withdraw/pending")
    public ResponseEntity<List<PendingWithdrawDto>> getPendingWithdrawRequests(){

        return ResponseEntity.ok(
                adminService.getPendingWithdrawRequests()
        );

    }

    @PostMapping("/withdraw/{id}/approve")
    public ResponseEntity<String> approveWithdraw(
            @PathVariable Long id){

        return ResponseEntity.ok(
                adminService.approveWithdraw(id)
        );

    }

    @PostMapping("/withdraw/{id}/reject")
    public ResponseEntity<String> rejectWithdraw(
            @PathVariable Long id){

        return ResponseEntity.ok(
                adminService.rejectWithdraw(id)
        );

    }

    @GetMapping("/daily-income")
    public ResponseEntity<List<DailyIncomeDetailsDto>> getDailyIncomeDetails() {

        return ResponseEntity.ok(
                adminService.getDailyIncomeDetails()
        );
    }



}
