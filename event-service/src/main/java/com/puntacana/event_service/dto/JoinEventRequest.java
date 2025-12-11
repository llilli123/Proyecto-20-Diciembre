package com.puntacana.event_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JoinEventRequest {

    @NotBlank
    private String roomNumber;

    private String guestName;
}
