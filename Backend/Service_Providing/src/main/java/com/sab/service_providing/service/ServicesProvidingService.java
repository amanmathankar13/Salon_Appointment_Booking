package com.sab.service_providing.service;

import java.util.Set;

import com.sab.service_providing.entity.ServicesProviding;
import com.sab.service_providing.payload.dto.CategoryDTO;
import com.sab.service_providing.payload.dto.SalonDTO;
import com.sab.service_providing.payload.dto.ServiceDTO;

public interface ServicesProvidingService {
    
    ServicesProviding createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO);
    ServicesProviding updateService(Long serviceId, ServicesProviding service) throws Exception;
    Set<ServicesProviding> getAllServicesBySalonId(Long salonId, Long categoryId);
    ServicesProviding getServiceById(Long serviceId) throws Exception;
    Set<ServicesProviding> getServicesByIds(Set<Long> ids);
}
