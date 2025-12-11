package com.puntacana.zone_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.puntacana.zone_service.domain.ZoneStatus;
import com.puntacana.zone_service.domain.ZoneType;
import com.puntacana.zone_service.dto.CreateZoneRequest;
import com.puntacana.zone_service.dto.UpdateZoneRequest;
import com.puntacana.zone_service.dto.UpdateZoneStatusRequest;
import com.puntacana.zone_service.dto.ZoneResponse;
import com.puntacana.zone_service.service.ZoneService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    // HÚESPED / PÚBLICO (via Gateway)

    @GetMapping
    public ResponseEntity<List<ZoneResponse>> listZones(
            @RequestParam(required = false) ZoneType type,
            @RequestParam(required = false) ZoneStatus status
    ) {
        return ResponseEntity.ok(zoneService.listZones(type, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneResponse> getZone(@PathVariable Long id) {
        return ResponseEntity.ok(zoneService.getZone(id));
    }

    // ADMIN

    @PostMapping
    public ResponseEntity<ZoneResponse> createZone(
            @Valid @RequestBody CreateZoneRequest request
    ) {
        return ResponseEntity.ok(zoneService.createZone(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZoneResponse> updateZone(
            @PathVariable Long id,
            @Valid @RequestBody UpdateZoneRequest request
    ) {
        return ResponseEntity.ok(zoneService.updateZone(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ZoneResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateZoneStatusRequest request
    ) {
        return ResponseEntity.ok(zoneService.updateStatus(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();
    }
}