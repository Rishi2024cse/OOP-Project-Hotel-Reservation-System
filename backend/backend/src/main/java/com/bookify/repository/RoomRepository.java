package com.bookify.repository;

import com.bookify.entity.Hotel;
import com.bookify.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    List<Room> findByHotel(Hotel hotel);
    List<Room> findByHotelAndIsAvailable(Hotel hotel, Boolean isAvailable);
    
    @Query("SELECT r FROM Room r WHERE r.hotel = :hotel AND r.isAvailable = true AND " +
           "NOT EXISTS (SELECT b FROM Booking b WHERE b.room = r AND b.status != 'CANCELLED' AND " +
           "((b.checkInDate <= :checkOut AND b.checkOutDate > :checkIn)))")
    List<Room> findAvailableRooms(@Param("hotel") Hotel hotel, 
                                 @Param("checkIn") LocalDate checkIn, 
                                 @Param("checkOut") LocalDate checkOut);
}
