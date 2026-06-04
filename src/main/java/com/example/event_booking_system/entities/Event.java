package com.example.event_booking_system.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    private UUID id;

    @NotBlank (message = "Event title is required.")
    private  String title;

    @NotNull(message = "Event date is required.")
    @Future(message = "Event date must be a future date and time.")
    private LocalDateTime eventDate;

    @NotNull(message = "Total tickets count is required.")
    @Min(value = 1, message = "Total tickets must be at least 1.")
    private Integer totalTickets;

//    @NotNull(message = "Available tickets count is required.")
    @Min(value = 0, message = "Available tickets cannot be less than 0.")
    private Integer availableTickets;

    @NotNull(message = "Ticket price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Ticket price must be greater than 0.0.")
    private BigDecimal ticketPrice;
}
