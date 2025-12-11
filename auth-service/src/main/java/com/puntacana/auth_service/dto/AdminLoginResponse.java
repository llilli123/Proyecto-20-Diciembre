package com.puntacana.auth_service.dto;

import com.puntacana.auth_service.domain.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AdminLoginResponse {

    private String token;
    private String username;
    private UserRole role;
    private Instant expiresAt;
}
