package com.sab.salon_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sab.salon_service.payload.dto.SalonDTO;
import com.sab.salon_service.payload.dto.UserDTO;
import com.sab.salon_service.service.SalonService;
import com.sab.salon_service.service.clients.UserFeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/salons")
public class SalonController {

    @Autowired
    private SalonService salonService;

    @Autowired
    private UserFeignClient userFeignClient;

    @PostMapping("/create")
    public ResponseEntity<SalonDTO> createSalons(@RequestBody SalonDTO salonDTO, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO userDTO = userFeignClient.getUserFromJwtToken(jwt).getBody();
        if (userDTO == null) {
            throw new Exception("User not found");
        }
        return new ResponseEntity<>(salonService.createSalon(salonDTO,userDTO).toDTO(), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<SalonDTO>> getSalons() {
        List<SalonDTO> list = salonService.getAllSalons().stream().map(salon-> salon.toDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SalonDTO> updateSalons(@RequestBody SalonDTO salonDTO, @PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO userDTO = userFeignClient.getUserFromJwtToken(jwt).getBody();
        if (userDTO == null) {
            throw new Exception("User not found");
        }
        return new ResponseEntity<>(salonService.updateSalon(salonDTO,userDTO,id).toDTO(), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SalonDTO> getSalonById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(salonService.getSalonById(id).toDTO());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<SalonDTO>> searchSalons(@RequestParam String city) {
        return ResponseEntity.ok(salonService.searchSalonByCity(city).stream().map(salon-> salon.toDTO()).collect(Collectors.toList()));
    }

    @GetMapping("/owner")
    public ResponseEntity<SalonDTO> getByOwnerId(@RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO userDTO = userFeignClient.getUserFromJwtToken(jwt).getBody();
        if (userDTO == null) {
            throw new Exception("User not found");
        }
        return ResponseEntity.ok(salonService.getSalonByOwnerId(userDTO.getId()).toDTO());
    }

   

}