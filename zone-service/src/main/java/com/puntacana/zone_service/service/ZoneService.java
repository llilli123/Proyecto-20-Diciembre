package com.puntacana.zone_service.service;

import com.puntacana.zone_service.domain.Zone;
import com.puntacana.zone_service.domain.ZoneStatus;
import com.puntacana.zone_service.domain.ZoneType;
import com.puntacana.zone_service.dto.CreateZoneRequest;
import com.puntacana.zone_service.dto.UpdateZoneRequest;
import com.puntacana.zone_service.dto.UpdateZoneStatusRequest;
import com.puntacana.zone_service.dto.ZoneResponse;
import com.puntacana.zone_service.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;

    @Transactional
    public ZoneResponse createZone(CreateZoneRequest request) {
        LocalDateTime now = LocalDateTime.now();

        Zone zone = Zone.builder()
                .name(request.getName())
                .description(request.getDescription())
                .photoUrl(request.getPhotoUrl())
                .type(request.getType())
                .status(ZoneStatus.OPERATING) // por defecto operando
                .openingTime(request.getOpeningTime())
                .closingTime(request.getClosingTime())
                .mapLocationId(request.getMapLocationId())
                .createdAt(now)
                .updatedAt(now)
                .build();

        zone = zoneRepository.save(zone);

        return mapToResponse(zone);
    }

    @Transactional
    public ZoneResponse updateZone(Long id, UpdateZoneRequest request) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        zone.setName(request.getName());
        zone.setDescription(request.getDescription());
        zone.setPhotoUrl(request.getPhotoUrl());
        zone.setType(request.getType());
        zone.setOpeningTime(request.getOpeningTime());
        zone.setClosingTime(request.getClosingTime());
        zone.setMapLocationId(request.getMapLocationId());
        zone.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(zone);
    }

    @Transactional
    public void deleteZone(Long id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        zoneRepository.delete(zone);
    }

    @Transactional(readOnly = true)
    public ZoneResponse getZone(Long id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        return mapToResponse(zone);
    }

    /**
     * Listado con filtros opcionales por tipo y estado.
     * Si ambos son null → todas las zonas.
     */
    @Transactional(readOnly = true)
    public List<ZoneResponse> listZones(ZoneType type, ZoneStatus status) {
        List<Zone> zones;

        if (type != null && status != null) {
            zones = zoneRepository.findByTypeAndStatus(type, status);
        } else if (type != null) {
            zones = zoneRepository.findByType(type);
        } else if (status != null) {
            zones = zoneRepository.findByStatus(status);
        } else {
            zones = zoneRepository.findAll();
        }

        return zones.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public ZoneResponse updateStatus(Long id, UpdateZoneStatusRequest request) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        zone.setStatus(request.getStatus());
        zone.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(zone);
    }

    private ZoneResponse mapToResponse(Zone zone) {
        return ZoneResponse.builder()
                .id(zone.getId())
                .name(zone.getName())
                .description(zone.getDescription())
                .photoUrl(zone.getPhotoUrl())
                .type(zone.getType())
                .status(zone.getStatus())
                .openingTime(zone.getOpeningTime())
                .closingTime(zone.getClosingTime())
                .mapLocationId(zone.getMapLocationId())
                .createdAt(zone.getCreatedAt())
                .updatedAt(zone.getUpdatedAt())
                .build();
    }
}