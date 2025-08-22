package com.sab.category_service.service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.sab.category_service.payload.dto.SalonDTO;

@FeignClient("SALONSERVICE")
public interface SalonFeignClient {
    
    @GetMapping("/salons/owner")
    public ResponseEntity<SalonDTO> getByOwnerId(@RequestHeader("Authorization") String jwt) throws Exception;
}
