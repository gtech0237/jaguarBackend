package com.backend.investment.controller;

import com.backend.investment.dto.RechargeResponseDto;
import com.backend.investment.dto.UserResponseDto;
import com.backend.investment.service.IRechargeService;
import com.backend.investment.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recharge")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RechargeController {

    private final IRechargeService rechargeService;
    private final IUserService userService;

    @GetMapping("/history")
    public ResponseEntity<List<RechargeResponseDto>> getRechargeHistory(
            Authentication authentication) {

        String phone = authentication.getName();

        UserResponseDto user =
                userService.getLoggedInUser(phone);

        return ResponseEntity.ok(
                rechargeService.rechargeHistory(user.getId())
        );
    }
}