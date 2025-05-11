package com.sab.salon_service.service;

import java.util.List;

import com.sab.salon_service.entity.Salon;
import com.sab.salon_service.payload.dto.SalonDTO;
import com.sab.salon_service.payload.dto.UserDTO;

public interface SalonService {

    Salon createSalon(SalonDTO salonDTO, UserDTO userDTO);
    Salon updateSalon(SalonDTO salonDTO, UserDTO user, Long id) throws Exception;
    List<Salon> getAllSalons();
    Salon getSalonById(Long id) throws Exception;
    List<Salon> searchSalonByCity(String city);
    Salon getSalonByOwnerId(Long ownerId);

}
