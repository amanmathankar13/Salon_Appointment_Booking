package com.sab.booking_service.service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.sab.booking_service.dto.SalonDTO;


@FeignClient("SALONSERVICE")
public interface SalonFeignClient {
    
    @GetMapping("/salons/owner")
    public ResponseEntity<SalonDTO> getByOwnerId(@RequestHeader("Authorization") String jwt) throws Exception;

    @GetMapping("/salons/get/{id}")
    public ResponseEntity<SalonDTO> getById(@PathVariable("id") Long id) throws Exception;
}
