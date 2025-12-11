package com.puntacana.event_service.repository;

import com.puntacana.event_service.domain.Event;
import com.puntacana.event_service.domain.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByStatusAndStartTimeAfterOrderByStartTimeAsc(EventStatus status, LocalDateTime now);
}
