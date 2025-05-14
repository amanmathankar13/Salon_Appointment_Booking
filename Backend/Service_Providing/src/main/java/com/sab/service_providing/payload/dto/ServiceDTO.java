package com.sab.service_providing.payload.dto;


import com.sab.service_providing.entity.ServicesProviding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {
    private Long id;

    private String name;

    private String description;

    private String image;

    private int price;

    private Long categoryId;

    private int duration;

    private Long salonId;

    public ServicesProviding toEntity(){
        return new ServicesProviding(this.id, this.name, this.description, this.image, this.price, this.categoryId, this.duration, this.salonId);
    }
}
