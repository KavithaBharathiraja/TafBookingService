package com.example.TafBookingService.Models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Booking {
    private Long id;
    private User user;
    private Flight flight;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
