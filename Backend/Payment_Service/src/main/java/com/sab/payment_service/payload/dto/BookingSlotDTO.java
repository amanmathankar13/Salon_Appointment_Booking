package com.sab.payment_service.payload.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingSlotDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
