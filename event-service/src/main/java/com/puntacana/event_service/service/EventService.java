package com.puntacana.event_service.service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.puntacana.event_service.domain.Event;
import com.puntacana.event_service.domain.EventAttendance;
import com.puntacana.event_service.domain.EventStatus;
import com.puntacana.event_service.dto.CreateEventRequest;
import com.puntacana.event_service.dto.EventResponse;
import com.puntacana.event_service.dto.JoinEventRequest;
import com.puntacana.event_service.dto.MyEventResponse;
import com.puntacana.event_service.dto.UpdateEventRequest;
import com.puntacana.event_service.repository.EventAttendanceRepository;
import com.puntacana.event_service.repository.EventRepository;

@Service

public class EventService {

    private final EventRepository eventRepository;
    private final EventAttendanceRepository attendanceRepository; 

    public EventService(EventRepository eventRepository,
                        EventAttendanceRepository attendanceRepository) {
        this.eventRepository = eventRepository;
        this.attendanceRepository = attendanceRepository;
    }
    // ADMIN: crear
    @Transactional
    public EventResponse createEvent(CreateEventRequest request) {
        if (request.getEndTime().isBefore(request.getStartTime())) {
            throw new RuntimeException("La hora de fin no puede ser antes que la de inicio");
        }

        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .capacity(request.getCapacity())
                .location(request.getLocation())
                .status(EventStatus.PUBLISHED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        event = eventRepository.save(event);

        return mapToResponse(event, 0L);
    }

    // ADMIN: editar (usa UpdateEventRequest)
    @Transactional
    public EventResponse updateEvent(Long id, UpdateEventRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        if (request.getEndTime().isBefore(request.getStartTime())) {
            throw new RuntimeException("La hora de fin no puede ser antes que la de inicio");
        }

        long occupied = attendanceRepository.countByEventAndCancelledFalse(event);
        if (request.getCapacity() < occupied) {
            throw new RuntimeException("La nueva capacidad no puede ser menor que los inscritos actuales");
        }

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setCapacity(request.getCapacity());
        event.setLocation(request.getLocation());
        event.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(event, occupied);
    }

    // ADMIN: eliminar
    @Transactional
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        eventRepository.delete(event);
    }

    // HUÉSPED: listar eventos publicados
    @Transactional(readOnly = true)
    public List<EventResponse> listPublishedEvents() {
        LocalDateTime now = LocalDateTime.now();
        List<Event> events = eventRepository
                .findByStatusAndStartTimeAfterOrderByStartTimeAsc(EventStatus.PUBLISHED, now);

        return events.stream()
                .map(e -> mapToResponse(e, attendanceRepository.countByEventAndCancelledFalse(e)))
                .toList();
    }

    // HUÉSPED: detalle
    @Transactional(readOnly = true)
    public EventResponse getEventDetail(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        long occupied = attendanceRepository.countByEventAndCancelledFalse(event);
        return mapToResponse(event, occupied);
    }

    // HUÉSPED: unirse
    @Transactional
    public void joinEvent(Long eventId, JoinEventRequest request) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Evento no encontrado"));

    if (event.getStatus() == EventStatus.CANCELLED) {
        throw new ResponseStatusException(CONFLICT, "El evento está cancelado");
    }

    if (event.getStatus() == EventStatus.FULL) {
        throw new ResponseStatusException(CONFLICT, "El evento está lleno");
    }

    long occupied = attendanceRepository.countByEventAndCancelledFalse(event);

    if (occupied >= event.getCapacity()) {
        event.setStatus(EventStatus.FULL);
        // opcional: persistir el status
        eventRepository.save(event);
        throw new ResponseStatusException(CONFLICT, "Capacidad completa");
    }

    attendanceRepository.findByEventAndRoomNumberAndCancelledFalse(event, request.getRoomNumber())
            .ifPresent(a -> {
                throw new ResponseStatusException(CONFLICT, "Ya está inscrito");
            });

    EventAttendance attendance = EventAttendance.builder()
            .event(event)
            .roomNumber(request.getRoomNumber())
            .guestName(request.getGuestName())
            .joinedAt(LocalDateTime.now())
            .cancelled(false)
            .build();

    attendanceRepository.save(attendance);

    long newOccupied = occupied + 1;
    if (newOccupied >= event.getCapacity()) {
        event.setStatus(EventStatus.FULL);
        eventRepository.save(event);
    }
    }

    // HUÉSPED: salirse
    @Transactional
    public void leaveEvent(Long eventId, String roomNumber) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        EventAttendance attendance = attendanceRepository
                .findByEventAndRoomNumberAndCancelledFalse(event, roomNumber)
                .orElseThrow(() -> new RuntimeException("El huésped no está inscrito en este evento"));

        attendance.setCancelled(true);

        long occupied = attendanceRepository.countByEventAndCancelledFalse(event);
        if (event.getStatus() == EventStatus.FULL && occupied < event.getCapacity()) {
            event.setStatus(EventStatus.PUBLISHED);
        }
    }

    // HUÉSPED: mis eventos
    @Transactional(readOnly = true)
    public List<MyEventResponse> getMyEvents(String roomNumber) {
        List<EventAttendance> attendances =
                attendanceRepository.findByRoomNumberAndCancelledFalse(roomNumber);

        return attendances.stream()
                .map(a -> {
                    Event e = a.getEvent();
                    return MyEventResponse.builder()
                            .eventId(e.getId())
                            .title(e.getTitle())
                            .description(e.getDescription())
                            .startTime(e.getStartTime())
                            .endTime(e.getEndTime())
                            .status(e.getStatus())
                            .location(e.getLocation())
                            .build();
                })
                .toList();
    }

    private EventResponse mapToResponse(Event event, long occupied) {
        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .capacity(event.getCapacity())
                .occupiedSpots(occupied)
                .status(event.getStatus())
                .location(event.getLocation())
                .build();
    }
}
