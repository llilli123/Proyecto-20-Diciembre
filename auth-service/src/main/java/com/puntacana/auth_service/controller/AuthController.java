package com.puntacana.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puntacana.auth_service.dto.AdminLoginRequest;
import com.puntacana.auth_service.dto.AdminLoginResponse;
import com.puntacana.auth_service.dto.GuestLoginRequest;
import com.puntacana.auth_service.dto.GuestLoginResponse;
import com.puntacana.auth_service.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/guest/login")
    public ResponseEntity<GuestLoginResponse> guestLogin(
            @Valid @RequestBody GuestLoginRequest request) {
        return ResponseEntity.ok(authService.loginGuest(request));
    }

    @PostMapping("/admin/login")
    public ResponseEntity<AdminLoginResponse> adminLogin(
            @Valid @RequestBody AdminLoginRequest request) {
        return ResponseEntity.ok(authService.loginAdmin(request));
    }
}
