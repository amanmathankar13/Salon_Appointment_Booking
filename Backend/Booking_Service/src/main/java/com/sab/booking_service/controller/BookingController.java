package com.sab.booking_service.controller;

import com.sab.booking_service.domain.BookingStatus;
import com.sab.booking_service.domain.PaymentMethod;
import com.sab.booking_service.dto.*;
import com.sab.booking_service.entity.Booking;
import com.sab.booking_service.entity.SalonReport;
import com.sab.booking_service.service.BookingService;
import com.sab.booking_service.service.client.PaymentFeignClient;
import com.sab.booking_service.service.client.SalonFeignClient;
import com.sab.booking_service.service.client.ServiceProvidingFeignClient;
import com.sab.booking_service.service.client.UserFeignClient;

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

    @Autowired
    private SalonFeignClient salonFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ServiceProvidingFeignClient serviceProvidingFeignClient;

    @Autowired
    private PaymentFeignClient paymentFeignClient;

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest bookingRequest, @RequestParam("salonId") Long salonId, @RequestParam("salonId") PaymentMethod paymentMethod, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO userDTO = userFeignClient.getUserFromJwtToken(jwt).getBody();
        
        SalonDTO salonDTO = salonFeignClient.getById(salonId).getBody();
    
        Set<ServiceDTO> serviceDTOSet = serviceProvidingFeignClient.getAllServicesByID(bookingRequest.getServiceIds()).getBody();

        Booking booking = bookingService.createBooking(bookingRequest,userDTO,salonDTO,serviceDTOSet);

        paymentFeignClient.createPaymentLink(booking.toDTO(), paymentMethod, jwt);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingByCustomer(@RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO userDTO = userFeignClient.getUserFromJwtToken(jwt).getBody();
        if (userDTO == null || userDTO.getId() == null) {
            throw new Exception("User not found");
        }
        List<Booking> bookings = bookingService.getBookingsByCustomer(userDTO.getId());
        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingBySalon(@RequestHeader("Authorization") String jwt) throws Exception {
        SalonDTO salonDTO = salonFeignClient.getByOwnerId(jwt).getBody();
        if (salonDTO == null || salonDTO.getId() == null) {
            throw new Exception("Salon not found");
        }
        List<Booking> bookings = bookingService.getBookingsBySalon(salonDTO.getId());
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
    public ResponseEntity<SalonReport> getBookingReport(@RequestHeader("Authorization") String jwt) throws Exception {
        SalonDTO salonDTO = salonFeignClient.getByOwnerId(jwt).getBody();
        if (salonDTO == null || salonDTO.getId() == null) {
            throw new Exception("Salon not found");
        }
        return ResponseEntity.ok(bookingService.getSalonReport(salonDTO.getId()));
    }


    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings) {
        return bookings.stream().map(Booking::toDTO).collect(Collectors.toSet());
    }

}
