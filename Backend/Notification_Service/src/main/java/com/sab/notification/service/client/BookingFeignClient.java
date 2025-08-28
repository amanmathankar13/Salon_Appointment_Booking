package com.sab.notification.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sab.notification.payload.dto.BookingDTO;

@FeignClient("BOOKING_SERVICE")
public interface BookingFeignClient {

    @GetMapping("/bookings/get/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable("id") Long id) throws Exception;
}
