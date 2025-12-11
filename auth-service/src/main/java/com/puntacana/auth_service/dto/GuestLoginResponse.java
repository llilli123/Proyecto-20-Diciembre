package com.puntacana.auth_service.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuestLoginResponse {

    private String token;
    private String roomNumber;
    private String guestName;
    private Instant expiresAt;

    
}
