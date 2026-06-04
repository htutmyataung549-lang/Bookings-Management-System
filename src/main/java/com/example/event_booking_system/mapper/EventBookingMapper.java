package com.example.event_booking_system.mapper;

import com.example.event_booking_system.entities.Booking;
import com.example.event_booking_system.entities.Event;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface EventBookingMapper {

    List<Event> getAllEvents();

    List<Booking> getAllBookings();

    Event getEventById(@Param("id") UUID id);

    List<Booking> getBookingByCustomer(@Param("customerName") String customerName);

    int updateAvailableTickets(@Param("id") UUID id , @Param("quantity") Integer quantity );

    int insertBooking(Booking booking);

    int insertEvent (Event event);
}
