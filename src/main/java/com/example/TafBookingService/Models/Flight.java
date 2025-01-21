package com.example.TafBookingService.Models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Flight {

    private Long id;
    private String flightNumber;
    private String departure;
    private String arrival;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private int availableSeats;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
