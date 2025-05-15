package com.sab.booking_service.repository;

import com.sab.booking_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerId(Long customerId);
    List<Booking> findBySalonId(Long salonId);

    List<Booking> getBookingBySalonId(Long salonId);
}
