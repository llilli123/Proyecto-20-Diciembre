package com.puntacana.zone_service.dto;

import java.time.LocalTime;

import com.puntacana.zone_service.domain.ZoneType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateZoneRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String photoUrl;

    @NotNull
    private ZoneType type;

    private LocalTime openingTime;
    private LocalTime closingTime;

    private String mapLocationId;
}