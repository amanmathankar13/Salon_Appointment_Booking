package com.sab.booking_service.entity;

import com.sab.booking_service.domain.BookingStatus;
import com.sab.booking_service.dto.BookingDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private Long salonId;
    private Long customerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ElementCollection
    private Set<Long> serviceIds;

    private BookingStatus status = BookingStatus.PENDING;
    private int totalPrice;

    public BookingDTO toDTO() {
        return new BookingDTO(this.id,this.salonId,this.customerId,this.startTime,this.endTime,this.serviceIds,this.status,this.totalPrice);
    }


}
