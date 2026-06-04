package com.example.event_booking_system.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Booking {

    private UUID id;

    @NotNull(message = "Event ID is required.")
    private UUID eventId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotNull(message = "Ticket quantity is required.")
    @Min(value = 1, message = "Ticket quantity must be at least 1.")
    private Integer quantity;

    private BigDecimal totalAmount;

    private LocalDateTime bookingDate;

    private String eventTitle;

}
