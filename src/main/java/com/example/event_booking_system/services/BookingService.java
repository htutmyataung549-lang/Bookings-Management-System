package com.example.event_booking_system.services;

import com.example.event_booking_system.entities.Booking;
import com.example.event_booking_system.entities.Event;
import com.example.event_booking_system.mapper.EventBookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final EventBookingMapper eventBookingMapper;

    public List<Event> getAllEvents() {
        return eventBookingMapper.getAllEvents();
    }

    public List<Booking> getAllBookings() {
        return eventBookingMapper.getAllBookings();
    }

    public List<Booking> getBookingsByCustomer(String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }
        return eventBookingMapper.getBookingByCustomer(customerName);
    }

    public Event getEventById(String id){
        try {
            if (id == null || id.trim().isEmpty()) {
                return null;
            }
            UUID uuid = UUID.fromString(id);
            return eventBookingMapper.getEventById(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Transactional
    public Event createEvent(Event event) {
        event.setId(UUID.randomUUID());
        event.setAvailableTickets(event.getTotalTickets());
        eventBookingMapper.insertEvent(event);
        return event;
    }

    @Transactional
    public Booking bookTickets(Booking booking) {
        Event event = eventBookingMapper.getEventById(booking.getEventId());
        if (event == null) {
            throw new RuntimeException("Event not found!");
        }

        if (event.getAvailableTickets() < booking.getQuantity()) {
            throw new RuntimeException("Not enough tickets! Remaining: " + event.getAvailableTickets() + " tickets.");
        }

        BigDecimal totalPrice = event.getTicketPrice().multiply(new BigDecimal(booking.getQuantity()));
        booking.setTotalAmount(totalPrice);

        int updatedRows = eventBookingMapper.updateAvailableTickets(event.getId(), booking.getQuantity());
        if (updatedRows == 0) {
            throw new RuntimeException("Booking failed due to not enough tickets. Try again.");
        }

        booking.setId(UUID.randomUUID());
        booking.setBookingDate(LocalDateTime.now());

        eventBookingMapper.insertBooking(booking);
        return booking;
    }
}