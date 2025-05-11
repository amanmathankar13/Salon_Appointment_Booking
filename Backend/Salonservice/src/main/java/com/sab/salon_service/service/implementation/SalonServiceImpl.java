package com.sab.salon_service.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sab.salon_service.entity.Salon;
import com.sab.salon_service.payload.dto.SalonDTO;
import com.sab.salon_service.payload.dto.UserDTO;
import com.sab.salon_service.repository.SalonRepository;
import com.sab.salon_service.service.SalonService;

@Service
public class SalonServiceImpl implements SalonService {

    @Autowired
    private SalonRepository salonRepository;
    
    @Override
    public Salon createSalon(SalonDTO salonDTO, UserDTO userDTO) {
        Salon salon = salonRepository.save(salonDTO.toEntity());
        salon.setOwnerId(userDTO.getId());
        return salon;
    }

    @Override
    public Salon updateSalon(SalonDTO salonDTO,UserDTO user, Long id) throws Exception {
        Salon salon = salonRepository.findById(id).orElse(null);
        if (salon != null && salon.getOwnerId().equals(user.getId())) {
            salon.setName(salonDTO.getName());
            salon.setAddress(salonDTO.getAddress());
            salon.setPhoneNumber(salonDTO.getPhoneNumber());
            salon.setCity(salonDTO.getCity());
            salon.setImages(salonDTO.getImages());
            salon.setEmail(salonDTO.getEmail());
            salon.setOwnerId(user.getId());
            salon.setOpenTime(salonDTO.getOpenTime());
            salon.setCloseTime(salonDTO.getCloseTime());
            return salonRepository.save(salon);
        }
        throw new Exception("Salon not found");
    }


    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long id) throws Exception {
        return salonRepository.findById(id).orElseThrow(()-> new Exception("Salon not found"));
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        return salonRepository.searchSalons(city);
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return salonRepository.findByOwnerId(ownerId);
    }
    
}
