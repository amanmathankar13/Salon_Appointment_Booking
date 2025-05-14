package com.sab.service_providing.service.implementation;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sab.service_providing.entity.ServicesProviding;
import com.sab.service_providing.payload.dto.CategoryDTO;
import com.sab.service_providing.payload.dto.SalonDTO;
import com.sab.service_providing.payload.dto.ServiceDTO;
import com.sab.service_providing.repository.ServiceProvidingRepository;
import com.sab.service_providing.service.ServicesProvidingService;

@Service
public class ServicesProvidingServiceImpl implements ServicesProvidingService {

    @Autowired
    private ServiceProvidingRepository servicesProvidingRepository;

    @Override
    public ServicesProviding createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO) {
        ServicesProviding servicesProviding = servicesProvidingRepository.save(serviceDTO.toEntity());
        servicesProviding.setCategoryId(categoryDTO.getId());
        servicesProviding.setSalonId(salonDTO.getId());
        servicesProvidingRepository.save(servicesProviding);
        return servicesProviding;
    }

    @Override
    public ServicesProviding updateService(Long serviceId, ServicesProviding service) throws Exception {
        ServicesProviding existingService = servicesProvidingRepository.findById(serviceId).orElse(null);
        if (existingService != null) {
            existingService.setName(service.getName());
            existingService.setDescription(service.getDescription());
            existingService.setPrice(service.getPrice());
            existingService.setDuration(service.getDuration());
            existingService.setImage(service.getImage());
            return servicesProvidingRepository.save(existingService);
        }
        throw new Exception("Service not exist" + serviceId);
    }

    @Override
    public Set<ServicesProviding> getAllServicesBySalonId(Long salonId, Long categoryId) {
        Set<ServicesProviding> servicesProviding = servicesProvidingRepository.findBySalonId(salonId);
        if(categoryId!=null){
            servicesProviding = servicesProviding.stream()
            .filter(service ->
            service.getCategoryId()!=null && service.getCategoryId()==categoryId).collect(Collectors.toSet());
        }
        return servicesProviding;
    }

    @Override
    public ServicesProviding getServiceById(Long serviceId) throws Exception {
        return servicesProvidingRepository.findById(serviceId).orElseThrow(()-> new Exception("Service not found by id "+ serviceId));
    }

    @Override
    public Set<ServicesProviding> getServicesByIds(Set<Long> ids) {
        return new HashSet<>(servicesProvidingRepository.findAllById(ids));
    }
    
    
}
