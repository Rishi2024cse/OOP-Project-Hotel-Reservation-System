package com.bookify.service;

import com.bookify.entity.Hotel;
import com.bookify.entity.Room;
import com.bookify.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelService hotelService;

    public List<Room> getRoomsByHotel(UUID hotelId) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        return roomRepository.findByHotel(hotel);
    }

    public List<Room> getAvailableRooms(UUID hotelId, LocalDate checkIn, LocalDate checkOut) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        return roomRepository.findAvailableRooms(hotel, checkIn, checkOut);
    }

    public Room getRoomById(UUID id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(UUID id, Room roomDetails) {
        Room room = getRoomById(id);
        
        room.setRoomNumber(roomDetails.getRoomNumber());
        room.setRoomType(roomDetails.getRoomType());
        room.setCapacity(roomDetails.getCapacity());
        room.setPricePerNight(roomDetails.getPricePerNight());
        room.setAmenities(roomDetails.getAmenities());
        room.setIsAvailable(roomDetails.getIsAvailable());
        
        return roomRepository.save(room);
    }

    public void deleteRoom(UUID id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }

    public long getRoomCount() {
        return roomRepository.count();
    }
}
