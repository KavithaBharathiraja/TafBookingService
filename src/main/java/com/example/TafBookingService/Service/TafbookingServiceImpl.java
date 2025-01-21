package com.example.TafBookingService.Service;

import com.example.TafBookingService.Controller.BookingLimitExceededException;
import com.example.TafBookingService.Models.Booking;
import com.example.TafBookingService.Models.Flight;
import com.example.TafBookingService.Models.User;
import com.example.TafBookingService.Service.Interfaces.TafbookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TafbookingServiceImpl implements TafbookingService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/datastore/bookings";
    private static final String FLIGHT_URL = "http://localhost:8080/datastore/flights";
    private static final String USER_URL = "http://localhost:8080/datastore/users";

    private static final int MAX_BOOKING_LIMIT = 12;

    @Override
    public Booking createBooking(Booking booking) {
        try {
            String flightUrl = FLIGHT_URL + "/" + booking.getFlight().getId();
            Flight flight = restTemplate.getForObject(flightUrl, Flight.class);
            if (flight == null) {
                throw new RuntimeException("Flight not found for id: " + booking.getFlight().getId());
            }

            String bookingUrl = BASE_URL + "/flight/" + flight.getId();
            ResponseEntity<List> response = restTemplate.exchange(bookingUrl, HttpMethod.GET, null, List.class);
            if (response.getBody().size() >= MAX_BOOKING_LIMIT) {
                throw new BookingLimitExceededException("Booking limit reached. You cannot book this flight.");
            }

            String userUrl = USER_URL + "/" + booking.getUser().getId();
            User user = restTemplate.getForObject(userUrl, User.class);
            if (user == null) {
                throw new RuntimeException("User not found for id: " + booking.getUser().getId());
            }

            booking.setUser(user);
            booking.setFlight(flight);
            booking.setCreatedAt(LocalDateTime.now());
            booking.setUpdatedAt(LocalDateTime.now());

            ResponseEntity<Booking> responseEntity = restTemplate.postForEntity(BASE_URL, booking, Booking.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error creating booking: " + e.getMessage());
        }
    }


    @Override
    public List<Booking> getBookingsByFlightId(Long flightId) {
        String url = BASE_URL + "/flight/" + flightId;
        ResponseEntity<List<Booking>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Booking>>() {}
        );
        return response.getBody();
    }

    @Override
    public Booking getBooking(Long bookingId) {
        String url = BASE_URL + "/" + bookingId;
        ResponseEntity<Booking> response = restTemplate.getForEntity(url, Booking.class);
        return response.getBody();
    }

    @Override
    public Booking updateBooking(Long bookingId, Booking booking) {
        String url = BASE_URL + "/" + bookingId;
        restTemplate.put(url, booking);
        return booking;
    }

    @Override
    public void deleteBooking(Long bookingId) {
        String url = BASE_URL + "/" + bookingId;
        restTemplate.delete(url);
    }

    @Override
    public List<Booking> getAllBookings() {
        ResponseEntity<List<Booking>> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Booking>>() {}
        );
        return response.getBody();
    }
}
