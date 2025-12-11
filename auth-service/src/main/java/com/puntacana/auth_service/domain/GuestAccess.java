package com.puntacana.auth_service.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "guest_access")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String roomNumber;

    @Column(nullable = false, length = 100)
    private String accessToken;

    @Column(length = 150)
    private String guestName;

    @Column(nullable = false)
    private LocalDateTime validFrom;

    @Column(nullable = false)
    private LocalDateTime validTo;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;
}

