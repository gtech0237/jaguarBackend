

package com.backend.investment.controller;

import com.backend.investment.dto.LoginRequest;
import com.backend.investment.dto.LoginResponse;
import com.backend.investment.dto.RegisterRequest;
import com.backend.investment.dto.UserResponseDto;
import com.backend.investment.entity.User;
import com.backend.investment.service.IAuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final IAuthService authService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {

        String response = "api Successfully run";

        return ResponseEntity.ok(response);
    }
    /**
     * User Registration
     * POST: /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        String response = authService.register(request);

        return ResponseEntity.ok(response);
    }

    /**
     * User Login
     * POST: /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.forgotPassword(request)
        );
    }

}