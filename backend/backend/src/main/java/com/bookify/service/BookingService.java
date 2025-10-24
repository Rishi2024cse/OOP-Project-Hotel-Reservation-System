package com.bookify.service;

import com.bookify.dto.BookingRequest;
import com.bookify.entity.Booking;
import com.bookify.entity.Hotel;
import com.bookify.entity.Profile;
import com.bookify.entity.Room;
import com.bookify.repository.BookingRepository;
import com.bookify.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomService roomService;

    public List<Booking> getUserBookings(UUID userId) {
        Profile user = profileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return bookingRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findByOrderByCreatedAtDesc();
    }

    public Booking getBookingById(UUID id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Transactional
    public Booking createBooking(UUID userId, BookingRequest request) {
        Profile user = profileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Hotel hotel = hotelService.getHotelById(request.getHotelId());
        Room room = roomService.getRoomById(request.getRoomId());

        // Check room availability
        if (!bookingRepository.isRoomAvailable(room, request.getCheckInDate(), request.getCheckOutDate(), null)) {
            throw new RuntimeException("Room is not available for the selected dates");
        }

        Booking booking = new Booking(user, hotel, room, request.getCheckInDate(), 
                                    request.getCheckOutDate(), request.getTotalPrice());
        
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(UUID id, BookingRequest request) {
        Booking booking = getBookingById(id);
        
        Hotel hotel = hotelService.getHotelById(request.getHotelId());
        Room room = roomService.getRoomById(request.getRoomId());

        // Check room availability (excluding current booking)
        if (!bookingRepository.isRoomAvailable(room, request.getCheckInDate(), request.getCheckOutDate(), id)) {
            throw new RuntimeException("Room is not available for the selected dates");
        }

        booking.setHotel(hotel);
        booking.setRoom(room);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setTotalPrice(request.getTotalPrice());
        
        return bookingRepository.save(booking);
    }

    public Booking updateBookingStatus(UUID id, Booking.BookingStatus status) {
        Booking booking = getBookingById(id);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    public Booking updatePaymentStatus(UUID id, Booking.PaymentStatus paymentStatus) {
        Booking booking = getBookingById(id);
        booking.setPaymentStatus(paymentStatus);
        return bookingRepository.save(booking);
    }

    public void deleteBooking(UUID id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
    }

    public long getBookingCount() {
        return bookingRepository.count();
    }

    public boolean isRoomAvailable(UUID roomId, java.time.LocalDate checkIn, java.time.LocalDate checkOut) {
        Room room = roomService.getRoomById(roomId);
        return bookingRepository.isRoomAvailable(room, checkIn, checkOut, null);
    }
}
