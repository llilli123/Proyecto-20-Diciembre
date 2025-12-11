package com.puntacana.auth_service.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.puntacana.auth_service.domain.GuestAccess;

public interface GuestAccessRepository
        extends JpaRepository<GuestAccess, Long> {

    Optional<GuestAccess> findByRoomNumberAndAccessTokenAndActiveTrueAndValidFromBeforeAndValidToAfter(
            String room,
            String token,
            LocalDateTime start,
            LocalDateTime end
    );
    
    Optional<GuestAccess> findFirstByRoomNumberAndAccessToken(
        String room,
        String token
);
}
