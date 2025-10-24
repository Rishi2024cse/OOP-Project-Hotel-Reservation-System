package com.bookify.service;

import com.bookify.entity.Hotel;
import com.bookify.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findByOrderByRatingDesc();
    }

    public List<Hotel> searchHotels(String searchTerm, BigDecimal maxPrice) {
        return hotelRepository.findHotelsWithFilters(searchTerm, maxPrice);
    }

    public Hotel getHotelById(UUID id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(UUID id, Hotel hotelDetails) {
        Hotel hotel = getHotelById(id);
        
        hotel.setName(hotelDetails.getName());
        hotel.setLocation(hotelDetails.getLocation());
        hotel.setDescription(hotelDetails.getDescription());
        hotel.setPricePerNight(hotelDetails.getPricePerNight());
        hotel.setRating(hotelDetails.getRating());
        hotel.setImageUrl(hotelDetails.getImageUrl());
        hotel.setAmenities(hotelDetails.getAmenities());
        
        return hotelRepository.save(hotel);
    }

    public void deleteHotel(UUID id) {
        Hotel hotel = getHotelById(id);
        hotelRepository.delete(hotel);
    }

    public long getHotelCount() {
        return hotelRepository.count();
    }
}
