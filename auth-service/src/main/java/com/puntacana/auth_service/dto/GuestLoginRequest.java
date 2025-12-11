package com.puntacana.auth_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GuestLoginRequest {
    @NotBlank
    private String roomNumber;

    @NotBlank
    private String accessToken;
}
