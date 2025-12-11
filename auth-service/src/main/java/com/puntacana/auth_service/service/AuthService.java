package com.puntacana.auth_service.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.puntacana.auth_service.domain.User;
import com.puntacana.auth_service.dto.AdminLoginRequest;
import com.puntacana.auth_service.dto.AdminLoginResponse;
import com.puntacana.auth_service.dto.GuestLoginRequest;
import com.puntacana.auth_service.dto.GuestLoginResponse;
import com.puntacana.auth_service.repository.GuestAccessRepository;
import com.puntacana.auth_service.repository.UserRepository;
import com.puntacana.auth_service.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final GuestAccessRepository guestAccessRepository;
    private final JwtTokenProvider jwtTokenProvider;

       /*public GuestLoginResponse loginGuest(GuestLoginRequest request) {
        GuestAccess guest = guestAccessRepository
            .findFirstByRoomNumberAndAccessToken(
                    request.getRoomNumber(),
                    request.getAccessToken()
            )
            .orElseThrow(() -> new RuntimeException("Credenciales de huésped inválidas o expiradas"));

    String token = jwtTokenProvider.generateToken(
            "guest:" + guest.getRoomNumber(),
            Map.of(
                    "type", "GUEST",
                    "roomNumber", guest.getRoomNumber(),
                    "guestName", guest.getGuestName()
            )
    );

    Instant expiresAt = jwtTokenProvider.getExpirationInstant();

    return GuestLoginResponse.builder()
            .token(token)
            .roomNumber(guest.getRoomNumber())
            .guestName(guest.getGuestName())
            .expiresAt(expiresAt)
            .build();
}*/

public GuestLoginResponse loginGuest(GuestLoginRequest request) {

        // TODO: versión MVP SIN validación real ni JWT
        // Simplemente devolvemos una respuesta fija para probar el flujo

        String roomNumber = request.getRoomNumber();
        String guestName  = "Huésped Demo";

        return GuestLoginResponse.builder()
                .token("dummy-token-" + roomNumber)  // token falso
                .roomNumber(roomNumber)
                .guestName(guestName)
                .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                .build();
    }

    public AdminLoginResponse loginAdmin(AdminLoginRequest request) {
        User user = userRepository
                .findByUsernameAndActiveTrue(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario o contraseña incorrectos"));

        if (!user.getPassword().equals(request.getPassword())) {
    throw new RuntimeException("Usuario o contraseña incorrectos");
        }


        String token = jwtTokenProvider.generateToken(
                "admin:" + user.getUsername(),
                Map.of(
                        "type", "ADMIN",
                        "username", user.getUsername(),
                        "role", user.getRole().name()
                )
        );

        Instant expiresAt = jwtTokenProvider.getExpirationInstant();

        return AdminLoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole())
                .expiresAt(expiresAt)
                .build();
    } 

    
}
