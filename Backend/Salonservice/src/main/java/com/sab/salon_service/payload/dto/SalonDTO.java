package com.sab.salon_service.payload.dto;

import java.time.LocalTime;
import java.util.List;

import com.sab.salon_service.entity.Salon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalonDTO {

    private Long id;

    private String name;

    private List<String> images;

    private String address;

    private String phoneNumber;

    private String email;

    private String city;

    private Long ownerId;

    private LocalTime openTime;

    private LocalTime closeTime;

    public Salon toEntity(){
        return new Salon(this.id, this.name, this.images, this.address, this.phoneNumber, this.email, this.city, this.ownerId, this.openTime, this.closeTime);
    }
}
