package com.bookify.controller;

import com.bookify.entity.Booking;
import com.bookify.service.BookingService;
import com.bookify.service.HotelService;
import com.bookify.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("hotels", hotelService.getHotelCount());
        stats.put("rooms", roomService.getRoomCount());
        stats.put("bookings", bookingService.getBookingCount());
        
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/recent")
    public ResponseEntity<List<Booking>> getRecentBookings(@RequestParam(defaultValue = "20") int limit) {
        List<Booking> bookings = bookingService.getAllBookings();
        // Limit the results
        List<Booking> limitedBookings = bookings.stream()
                .limit(limit)
                .toList();
        return ResponseEntity.ok(limitedBookings);
    }
}
