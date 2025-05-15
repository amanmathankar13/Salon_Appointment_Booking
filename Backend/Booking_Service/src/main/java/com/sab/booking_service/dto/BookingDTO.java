package com.sab.booking_service.dto;

import com.sab.booking_service.domain.BookingStatus;
import com.sab.booking_service.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

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

    public Booking toEntity() {
        return new Booking(this.id,this.salonId,this.customerId,this.startTime,this.endTime,this.serviceIds,this.status,this.totalPrice);
    }

}
