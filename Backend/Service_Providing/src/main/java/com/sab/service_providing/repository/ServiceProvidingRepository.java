package com.sab.service_providing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sab.service_providing.entity.ServicesProviding;
import java.util.Set;


public interface ServiceProvidingRepository extends JpaRepository<ServicesProviding,Long> {
    Set<ServicesProviding>  findBySalonId(Long salonId);
}
