package com.example.TafBookingService.Controller;

import com.example.TafBookingService.Models.Booking;
import com.example.TafBookingService.Service.Interfaces.TafbookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private TafbookingService bookingService;

    // Create a new booking
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking newBooking = bookingService.createBooking(booking);
        return ResponseEntity.status(201).body(newBooking); // Respond with 201 created status
    }

    // Get a booking by ID
    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBooking(bookingId);
        return ResponseEntity.ok(booking);
    }

    // Get all bookings
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    // Update a booking by ID
    @PutMapping("/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long bookingId, @RequestBody Booking booking) {
        Booking updatedBooking = bookingService.updateBooking(bookingId, booking);
        return ResponseEntity.ok(updatedBooking);
    }

    // Delete a booking by ID
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build(); // Respond with 204 No Content
    }
}
