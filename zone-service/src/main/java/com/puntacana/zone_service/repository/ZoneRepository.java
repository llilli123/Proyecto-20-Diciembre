package com.puntacana.zone_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.puntacana.zone_service.domain.Zone;
import com.puntacana.zone_service.domain.ZoneStatus;
import com.puntacana.zone_service.domain.ZoneType;

public interface ZoneRepository extends JpaRepository<Zone, Long> {

    List<Zone> findByStatus(ZoneStatus status);

    List<Zone> findByType(ZoneType type);

    List<Zone> findByTypeAndStatus(ZoneType type, ZoneStatus status);
}