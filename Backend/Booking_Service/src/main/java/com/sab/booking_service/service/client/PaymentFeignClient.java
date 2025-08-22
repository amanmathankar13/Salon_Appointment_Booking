package com.sab.booking_service.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.sab.booking_service.domain.PaymentMethod;
import com.sab.booking_service.dto.BookingDTO;
import com.sab.booking_service.dto.PaymentLinkResponse;

@FeignClient("PAYMENT_SERVICE")
public interface PaymentFeignClient {

    @PostMapping("/payment/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@RequestBody BookingDTO bookingDTO, @RequestParam PaymentMethod paymentMethod, @RequestHeader("Authorization") String jwt) throws Exception;
}
