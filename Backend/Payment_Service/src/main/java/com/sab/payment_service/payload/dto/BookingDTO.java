package com.sab.payment_service.payload.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

import com.sab.payment_service.domain.BookingStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private  Long id;
    private Long salonId;
    private Long customerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Set<Long> serviceIds;
    private BookingStatus status = BookingStatus.PENDING;
    private int totalPrice;

    

}
