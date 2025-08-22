package com.sab.payment_service.service.client;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sab.payment_service.payload.dto.ServiceDTO;


@FeignClient("SERVICE_PROVIDING")
public interface ServiceProvidingFeignClient {

    @GetMapping("/services-providing/list/{ids}")
    public ResponseEntity<Set<ServiceDTO>> getAllServicesByID(@PathVariable("ids") Set<Long> ids);
}
