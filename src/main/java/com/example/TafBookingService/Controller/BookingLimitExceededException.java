package com.example.TafBookingService.Controller;

public class BookingLimitExceededException extends RuntimeException {
    public BookingLimitExceededException(String message) {
        super(message);
    }
}
