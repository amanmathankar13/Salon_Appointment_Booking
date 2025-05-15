package com.sab.booking_service.service;

import com.sab.booking_service.domain.BookingStatus;
import com.sab.booking_service.dto.BookingRequest;
import com.sab.booking_service.dto.SalonDTO;
import com.sab.booking_service.dto.ServiceDTO;
import com.sab.booking_service.dto.UserDTO;
import com.sab.booking_service.entity.Booking;
import com.sab.booking_service.entity.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest bookingRequest, UserDTO userDTO, SalonDTO salonDTO, Set<ServiceDTO> serviceDTO) throws Exception;

    List<Booking> getBookingsByCustomer(Long customerId);
    List<Booking> getBookingsBySalon(Long salonId);
    Booking getBookingById(Long id) throws Exception;
    Booking updateBooking(Long bookingId, BookingStatus bookingStatus) throws Exception;
    List<Booking> getBookingsByDate(LocalDate date, Long salonId);
    SalonReport getSalonReport(Long salonId);
}
