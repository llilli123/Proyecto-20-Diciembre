package com.puntacana.zone_service.dto;

import com.puntacana.zone_service.domain.ZoneStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateZoneStatusRequest {

    @NotNull
    private ZoneStatus status;
}
