package com.puntacana.auth_service.dto;

import lombok.Data;

@Data
public class AdminLoginRequest {

    private String username;
    private String password;
}
