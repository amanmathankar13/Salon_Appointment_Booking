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
        return salonRepository.save(salonDTO.toEntity());
    }

    @Override
    public Salon updateSalon(SalonDTO salonDTO, Long id) {
        Salon salon = salonRepository.findById(id).orElse(null);
        if (salon != null) {
            salon.setName(salonDTO.getName());
            salon.setAddress(salonDTO.getAddress());
            salon.setPhoneNumber(salonDTO.getPhoneNumber());
            salon.setCity(salonDTO.getCity());
            salon.setImages(salonDTO.getImages());
            salon.setEmail(salonDTO.getEmail());
            salon.setOwnerId(salon.getOwnerId());
            salon.setOpenTime(salonDTO.getOpenTime());
            salon.setCloseTime(salonDTO.getCloseTime());
            return salonRepository.save(salon);
        }
        return null;
    }


    @Override
    public List<Salon> getAllSalons() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllSalons'");
    }

    @Override
    public Salon getSalonById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSalonById'");
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchSalonByCity'");
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSalonByOwnerId'");
    }
    
}
