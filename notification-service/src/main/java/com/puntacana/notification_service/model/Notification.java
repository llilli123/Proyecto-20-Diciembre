package com.puntacana.notification_service.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String title;

    @NotBlank
    @Column(nullable = false, length = 500)
    private String message;

    /**
     * Tipo de notificación:
     * - EVENT_UPDATE
     * - CLEANING_STATUS
     * - INCIDENT_ALERT
     * etc.
     */
    @NotBlank
    @Column(nullable = false, length = 50)
    private String type;

    /**
     * Puede ser GUEST, STAFF, ADMIN, etc.
     */
    @NotBlank
    @Column(nullable = false, length = 50)
    private String target;

    /**
     * Id lógico del destinatario (ej: id de huésped, id de habitación, etc.)
     */
    private String targetReference;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean readFlag;
}