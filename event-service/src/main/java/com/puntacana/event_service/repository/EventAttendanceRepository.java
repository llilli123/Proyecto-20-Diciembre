package com.puntacana.event_service.repository;

import com.puntacana.event_service.domain.Event;
import com.puntacana.event_service.domain.EventAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventAttendanceRepository extends JpaRepository<EventAttendance, Long> {

    long countByEventAndCancelledFalse(Event event);

    Optional<EventAttendance> findByEventAndRoomNumberAndCancelledFalse(Event event, String roomNumber);

    List<EventAttendance> findByRoomNumberAndCancelledFalse(String roomNumber);
}
