package com.sab.booking_service.dto;


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


}
