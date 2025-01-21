package com.example.TafBookingService.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Booking {

    @JsonProperty("user")
    private User user;

    @JsonProperty("flight")
    private Flight flight;

    private Long id;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
