package com.puntacana.event_service.dto;

import com.puntacana.event_service.domain.EventStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EventResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer capacity;
    private Long occupiedSpots;
    private EventStatus status;
    private String location;
}
