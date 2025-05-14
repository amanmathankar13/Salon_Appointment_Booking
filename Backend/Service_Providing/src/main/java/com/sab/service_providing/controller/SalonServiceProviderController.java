package com.sab.service_providing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sab.service_providing.entity.ServicesProviding;
import com.sab.service_providing.payload.dto.CategoryDTO;
import com.sab.service_providing.payload.dto.SalonDTO;
import com.sab.service_providing.payload.dto.ServiceDTO;
import com.sab.service_providing.service.ServicesProvidingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/service-providing/salon-owner")
public class SalonServiceProviderController {
    @Autowired
    private ServicesProvidingService serviceProviderService;

    @PostMapping("/create")
    public ResponseEntity<ServicesProviding> createService(@RequestBody ServiceDTO serviceDTO) {
        SalonDTO salonDTO  = new SalonDTO();
        salonDTO.setId(1L);
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategoryId());
        return ResponseEntity.ok(serviceProviderService.createService(salonDTO, serviceDTO, categoryDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ServicesProviding> putMethodName(@PathVariable Long id, @RequestBody ServicesProviding servicesProviding) throws Exception {
        return ResponseEntity.ok(serviceProviderService.updateService(id,servicesProviding));
    }
    
}
