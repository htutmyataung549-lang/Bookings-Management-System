package com.example.event_booking_system.controllers;

import com.example.event_booking_system.entities.ApiResponse;
import com.example.event_booking_system.entities.Booking;
import com.example.event_booking_system.entities.Event;
import com.example.event_booking_system.services.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("${root-context.route:/api}")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://bookings-management-system-frontend.vercel.app"
})public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/events")
    public ResponseEntity<ApiResponse> getAllEvents(){
        try {
            List<Event> events = bookingService.getAllEvents();
            ApiResponse response = new ApiResponse("Success" , "Fetched all events successfully.",events);
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            ApiResponse response = new ApiResponse("Error", "Internal Server Error: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/bookings")
    public ResponseEntity<ApiResponse> getAllBookings() {
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            ApiResponse response = new ApiResponse("Success", "Fetched all bookings successfully.", bookings);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("Error", "Internal Server Error: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/events")
    public ResponseEntity<ApiResponse> createEvent(@Valid @RequestBody Event event){
        try {
            Event savedEvent = bookingService.createEvent(event);

            ApiResponse response = new ApiResponse("Success", "Events Create Successfully.", savedEvent);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse("Fail", e.getMessage(), null);
            return ResponseEntity.status(400).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("Error", "Internal Server Error: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<ApiResponse> getEventById(@PathVariable("id") String id) {
        try {
            Event event = bookingService.getEventById(id);

            if (event == null) {
                ApiResponse response = new ApiResponse("Fail", "Event not found with ID: " + id, null);
                return ResponseEntity.status(404).body(response);
            }

            ApiResponse response = new ApiResponse("Success", "Fetched event details successfully.", event);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("Error", "Internal Server Error: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/bookings")
    public ResponseEntity<ApiResponse> createBooking(@Valid @RequestBody Booking booking) {
        try {
            Booking savedBooking = bookingService.bookTickets(booking);

            ApiResponse response = new ApiResponse("Success", "Tickets Booked Successfully.", savedBooking);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse("Fail", e.getMessage(), null);
            return ResponseEntity.status(400).body(response);

        } catch (RuntimeException e) {
            ApiResponse response = new ApiResponse("Fail", e.getMessage(), null);
            return ResponseEntity.status(400).body(response);

        } catch (Exception e) {
            ApiResponse response = new ApiResponse("Error", "Internal Server Error: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/booking/user")
    public ResponseEntity<ApiResponse> getMyBookings(@RequestParam("customerName") String customerName){
        try {
            List<Booking> myBookings = bookingService.getBookingsByCustomer(customerName);
            ApiResponse response = new ApiResponse("Success", "Fetched user's bookings successfully.", myBookings);
            return ResponseEntity.ok(response);
        }catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse("Fail", e.getMessage(), null);
            return ResponseEntity.status(400).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("Error", "Internal Server Error: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }
}

