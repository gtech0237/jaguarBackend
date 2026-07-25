package com.backend.investment.controller;

import com.backend.investment.dto.BankAccountDto;
import com.backend.investment.dto.UserResponseDto;
import com.backend.investment.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final IUserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getProfile(
            Authentication authentication) {

        String phone = authentication.getName();

        UserResponseDto user =
                userService.getLoggedInUser(phone);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/bank-account")
    public ResponseEntity<UserResponseDto> saveBankAccount(
            Authentication authentication,
            @RequestBody BankAccountDto dto){
        return ResponseEntity.ok(userService.saveBankAccount(authentication.getName(), dto));

    }
}