package com.puntacana.event_service.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event_attendance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK real en MySQL: event_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    // Columna real: room_number
    @Column(name = "room_number", nullable = false, length = 20)
    private String roomNumber;

    // Columna real: guest_name
    @Column(name = "guest_name", length = 150)
    private String guestName;

    // Columna real: joined_at
    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    // Columna real: cancelled
    @Column(name = "cancelled", nullable = false)
    private boolean cancelled;
}
