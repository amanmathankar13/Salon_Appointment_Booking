package com.sab.booking_service.controller;

import com.sab.booking_service.domain.BookingStatus;
import com.sab.booking_service.dto.*;
import com.sab.booking_service.entity.Booking;
import com.sab.booking_service.entity.SalonReport;
import com.sab.booking_service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest bookingRequest, @RequestParam("salonId") Long salonId) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(salonId);
        salonDTO.setOpenTime(LocalTime.now());
        salonDTO.setCloseTime(LocalTime.now().plusHours(8));
        Set<ServiceDTO> serviceDTOSet = new HashSet<>();
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setPrice(399);
        serviceDTO.setDuration(45);
        serviceDTO.setName("Hair Cut (Men)");
        serviceDTOSet.add(serviceDTO);

        return new ResponseEntity<>(bookingService.createBooking(bookingRequest,userDTO,salonDTO,serviceDTOSet), HttpStatus.CREATED);
    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingByCustomer(){
        List<Booking> bookings = bookingService.getBookingsByCustomer(1L);
        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingBySalon(){
        List<Booking> bookings = bookingService.getBookingsBySalon(1L);
        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable("id") Long id) throws Exception {
            return ResponseEntity.ok(bookingService.getBookingById(id).toDTO());
    }

    @PutMapping("/update/{id}/bookingStatus")
    public ResponseEntity<BookingDTO> updateBookingStatus(@PathVariable("id") Long id, @RequestParam BookingStatus bookingStatus) throws Exception {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingStatus).toDTO());
    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getBookedSlots(@PathVariable Long salonId, @RequestParam LocalDate date) throws Exception {
        List<Booking> bookings = bookingService.getBookingsByDate(date, salonId);
        List<BookingSlotDTO> slotDTOS = bookings.stream().map(booking -> {
            BookingSlotDTO bookingSlotDTO = new BookingSlotDTO();
            bookingSlotDTO.setStartTime(booking.getStartTime());
            bookingSlotDTO.setEndTime(booking.getEndTime());
            return bookingSlotDTO;
        }).toList();
        return ResponseEntity.ok(slotDTOS);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getBookingReport() throws Exception {
        return ResponseEntity.ok(bookingService.getSalonReport(1L));
    }


    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings) {
        return bookings.stream().map(Booking::toDTO).collect(Collectors.toSet());
    }

}
