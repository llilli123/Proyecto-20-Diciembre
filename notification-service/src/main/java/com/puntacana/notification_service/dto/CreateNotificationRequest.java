package com.puntacana.notification_service.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * TODO: DTO de entrada para crear una notificación.
 */
@Data
public class CreateNotificationRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String message;

    @NotBlank
    private String type;

    @NotBlank
    private String target;

    private String targetReference;
}