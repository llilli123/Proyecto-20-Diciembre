package com.puntacana.event_service.controller;

import com.puntacana.event_service.dto.CreateEventRequest;
import com.puntacana.event_service.dto.EventResponse;
import com.puntacana.event_service.dto.JoinEventRequest;
import com.puntacana.event_service.dto.MyEventResponse;
import com.puntacana.event_service.dto.UpdateEventRequest;
import com.puntacana.event_service.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // HUÉSPED

    @GetMapping
    public ResponseEntity<List<EventResponse>> listEvents() {
        return ResponseEntity.ok(eventService.listPublishedEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventDetail(id));
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<Void> joinEvent(
            @PathVariable Long id,
            @Valid @RequestBody JoinEventRequest request
    ) {
        eventService.joinEvent(id, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<Void> leaveEvent(
            @PathVariable Long id,
            @RequestParam String roomNumber
    ) {
        eventService.leaveEvent(id, roomNumber);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<MyEventResponse>> myEvents(
            @RequestParam String roomNumber
    ) {
        return ResponseEntity.ok(eventService.getMyEvents(roomNumber));
    }

    // ADMIN

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            @Valid @RequestBody CreateEventRequest request
    ) {
        return ResponseEntity.ok(eventService.createEvent(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEventRequest request
    ) {
        return ResponseEntity.ok(eventService.updateEvent(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
