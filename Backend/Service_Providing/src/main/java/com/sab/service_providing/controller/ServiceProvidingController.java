package com.sab.service_providing.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sab.service_providing.entity.ServicesProviding;
import com.sab.service_providing.service.ServicesProvidingService;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/services-providing")
public class ServiceProvidingController {

    @Autowired
    private ServicesProvidingService servicesProvidingService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<ServicesProviding>> getAllServicesBySalonId(@PathVariable("salonId") Long salonId, @RequestParam(required= false) Long categoryId) {
        return ResponseEntity.ok(servicesProvidingService.getAllServicesBySalonId(salonId, categoryId));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ServicesProviding> getServiceById(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(servicesProvidingService.getServiceById(id));
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<ServicesProviding>> getAllServicesByID(@PathVariable("ids") Set<Long> ids) {
        return ResponseEntity.ok(servicesProvidingService.getServicesByIds(ids));
    }
    
}
