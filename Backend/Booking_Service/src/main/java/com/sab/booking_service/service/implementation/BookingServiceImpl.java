package com.sab.booking_service.service.implementation;

import com.sab.booking_service.domain.BookingStatus;
import com.sab.booking_service.dto.BookingRequest;
import com.sab.booking_service.dto.SalonDTO;
import com.sab.booking_service.dto.ServiceDTO;
import com.sab.booking_service.dto.UserDTO;
import com.sab.booking_service.entity.Booking;
import com.sab.booking_service.entity.SalonReport;
import com.sab.booking_service.repository.BookingRepository;
import com.sab.booking_service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest bookingRequest, UserDTO userDTO, SalonDTO salonDTO, Set<ServiceDTO> serviceDTO) throws Exception {

        int totalDuration = serviceDTO.stream().mapToInt(ServiceDTO::getDuration).sum();
        LocalDateTime bookingStartTime = bookingRequest.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);
        Boolean isSlotAvailable = isTimeSlotAvailable(salonDTO, bookingStartTime, bookingEndTime);
        int totalPrice = serviceDTO.stream().mapToInt(ServiceDTO::getPrice).sum();
        Set<Long> idsList = serviceDTO.stream().mapToLong(ServiceDTO::getId).boxed().collect(Collectors.toSet());
        Booking booking = new Booking();
        if(!isSlotAvailable){
            throw new Exception("Time slot is not available");
        }
            booking.setCustomerId(userDTO.getId());
            booking.setSalonId(salonDTO.getId());
            booking.setServiceIds(idsList);
            booking.setStatus(BookingStatus.PENDING);
            booking.setStartTime(bookingStartTime);
            booking.setEndTime(bookingEndTime);
            booking.setTotalPrice(totalPrice);
        
        return bookingRepository.save(booking);
    }

    public Boolean isTimeSlotAvailable(SalonDTO salonDTO, LocalDateTime bookingStartTime, LocalDateTime bookingEndTime) throws Exception {
        LocalDateTime salonOpenTime = salonDTO.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salonDTO.getCloseTime().atDate(bookingStartTime.toLocalDate());

        System.out.println(bookingStartTime);
        System.out.println(bookingEndTime);
        System.out.println(salonOpenTime);
        System.out.println(salonCloseTime);

        if (bookingStartTime.isBefore(salonOpenTime)) {
            throw new Exception("Salon is not open");
        }
        if (bookingEndTime.isAfter(salonCloseTime)) {
            throw new Exception("Salon is closed");
        }
        List<Booking> existingBookings = getBookingsBySalon(salonDTO.getId());
        for (Booking existingBooking : existingBookings) {
            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existingBookingEndTime = existingBooking.getEndTime();
            if (bookingStartTime.isBefore(existingBookingEndTime) && bookingEndTime.isBefore(existingBookingEndTime)){
                throw new Exception("Slot not available, choose different slot time");

            }
            if (bookingStartTime.isEqual(existingBookingStartTime) || bookingEndTime.isEqual(existingBookingStartTime)) {
                throw new Exception("Slot not available, choose different slot time");
            }
        }
        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
       return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id) throws Exception {
        return bookingRepository.findById(id).orElseThrow(()-> new Exception("booking not found"));
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus bookingStatus) throws Exception {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(bookingStatus);
        return bookingRepository.save(booking);
    }



    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {
        List<Booking> allBookings = bookingRepository.getBookingBySalonId(salonId);
        if (date==null){
            return allBookings;
        }
        return  allBookings.stream().filter(booking -> isSameDate(booking.getStartTime(),date)|| isSameDate(booking.getEndTime(),date)).collect(Collectors.toList());
    }
    public boolean isSameDate(LocalDateTime startTime, LocalDate date) {
        return startTime.toLocalDate().equals(date);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking> bookings = getBookingsBySalon(salonId);
        Double totalEarnings = bookings.stream().mapToDouble(Booking::getTotalPrice).sum();
        Integer totalBookings = bookings.size();
        List<Booking> cancelledBookings = bookings.stream().filter(booking -> booking.getStatus().equals(BookingStatus.CANCELLED)).collect(Collectors.toList());
        Double refund = cancelledBookings.stream().mapToDouble(Booking::getTotalPrice).sum();
        SalonReport salonReport = new SalonReport();
        salonReport.setSalonId(salonId);
        salonReport.setCancelledBookings(cancelledBookings.size());
        salonReport.setTotalBookings(totalBookings);
        salonReport.setTotalEarnings(totalEarnings);
        salonReport.setTotalRefund(refund);

        return salonReport;

    }
}
