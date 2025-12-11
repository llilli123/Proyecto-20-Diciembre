package com.puntacana.zone_service.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.puntacana.zone_service.domain.ZoneStatus;
import com.puntacana.zone_service.domain.ZoneType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ZoneResponse {

    private Long id;
    private String name;
    private String description;
    private String photoUrl;
    private ZoneType type;
    private ZoneStatus status;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String mapLocationId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
