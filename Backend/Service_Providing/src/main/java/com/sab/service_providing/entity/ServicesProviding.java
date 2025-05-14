package com.sab.service_providing.entity;

import com.sab.service_providing.payload.dto.ServiceDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicesProviding {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private Long salonId;

    public ServiceDTO toDTO(){
        return new ServiceDTO(this.id, this.name, this.description, this.image, this.price, this.categoryId, this.duration, this.salonId);
    }

}