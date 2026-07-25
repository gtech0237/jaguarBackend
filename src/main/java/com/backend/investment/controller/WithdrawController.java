package com.backend.investment.controller;

import com.backend.investment.dto.UserResponseDto;
import com.backend.investment.dto.WithdrawRequestDto;
import com.backend.investment.dto.WithdrawResponseDto;
import com.backend.investment.service.IUserService;
import com.backend.investment.service.IWithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/withdraw")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WithdrawController {

    private final IWithdrawService withdrawService;
    private final IUserService userService;

    @PostMapping("/request")
    public ResponseEntity<WithdrawResponseDto> requestWithdraw(
            @RequestBody WithdrawRequestDto request){

        return ResponseEntity.ok(
                withdrawService.requestWithdraw(request)
        );

    }
    @GetMapping("/history")
    public ResponseEntity<List<WithdrawResponseDto>> withdrawHistory(
            Authentication authentication) {

        UserResponseDto user =
                userService.getLoggedInUser(authentication.getName());

        return ResponseEntity.ok(
                withdrawService.withdrawHistory(user.getId())
        );

    }

}