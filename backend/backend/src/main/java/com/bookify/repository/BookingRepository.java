package com.bookify.repository;

import com.bookify.entity.Booking;
import com.bookify.entity.Profile;
import com.bookify.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByUserOrderByCreatedAtDesc(Profile user);
    List<Booking> findByOrderByCreatedAtDesc();
    
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN false ELSE true END FROM Booking b " +
           "WHERE b.room = :room AND b.status != 'CANCELLED' AND " +
           "(:bookingId IS NULL OR b.id != :bookingId) AND " +
           "((b.checkInDate <= :checkOut AND b.checkOutDate > :checkIn))")
    boolean isRoomAvailable(@Param("room") Room room, 
                           @Param("checkIn") LocalDate checkIn, 
                           @Param("checkOut") LocalDate checkOut, 
                           @Param("bookingId") UUID bookingId);
}
