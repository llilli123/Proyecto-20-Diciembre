package com.puntacana.zone_service.dto;

import com.puntacana.zone_service.domain.ZoneType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class CreateZoneRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String photoUrl;

    @NotNull
    private ZoneType type;

    /**
     * Horario de operación
     */
    private LocalTime openingTime;
    private LocalTime closingTime;

    /**
     * Referencia a map-service
     */
    private String mapLocationId;
}
