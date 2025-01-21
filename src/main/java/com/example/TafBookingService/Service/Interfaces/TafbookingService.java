package com.example.TafBookingService.Service.Interfaces;

import com.example.TafBookingService.Models.Booking;

import java.util.List;

public interface TafbookingService {

    Booking createBooking(Booking booking);

    List<Booking> getAllBookings();

    List<Booking> getBookingsByFlightId(Long flightId);

    Booking getBooking(Long bookingId);

    Booking updateBooking(Long bookingId, Booking booking);

    void deleteBooking(Long bookingId);
}
