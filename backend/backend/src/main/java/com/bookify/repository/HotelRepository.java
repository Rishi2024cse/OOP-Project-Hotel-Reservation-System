package com.bookify.repository;

import com.bookify.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, UUID> {
    List<Hotel> findByOrderByRatingDesc();
    
    @Query("SELECT h FROM Hotel h WHERE " +
           "(:searchTerm IS NULL OR LOWER(h.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(h.location) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
           "(:maxPrice IS NULL OR h.pricePerNight <= :maxPrice) " +
           "ORDER BY h.rating DESC")
    List<Hotel> findHotelsWithFilters(@Param("searchTerm") String searchTerm, 
                                     @Param("maxPrice") BigDecimal maxPrice);
}
