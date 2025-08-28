package com.sab.notification.payload.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;

    private String type;

    private String description;

    private boolean isRead = false;

    private Long userId;

    private Long bookingId;

    private Long salonId;

    private LocalDateTime createdAt;

    private BookingDTO booking;


}
