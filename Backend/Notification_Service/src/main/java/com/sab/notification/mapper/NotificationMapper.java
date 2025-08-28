package com.sab.notification.mapper;

import com.sab.notification.entity.Notification;
import com.sab.notification.payload.dto.BookingDTO;
import com.sab.notification.payload.dto.NotificationDTO;

public class NotificationMapper {

    public static NotificationDTO toDTO(Notification notification, BookingDTO bookingDTO) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setBookingId(notification.getBookingId());
        dto.setUserId(notification.getUserId());
        dto.setSalonId(notification.getSalonId());
        dto.setType(notification.getType());
        dto.setDescription(notification.getDescription());
        dto.setRead(notification.isRead());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setBooking(bookingDTO);
        return dto;
    }

    public static Notification toEntity(NotificationDTO dto) {
        Notification notification = new Notification();
        notification.setId(dto.getId());
        notification.setBookingId(dto.getBookingId());
        notification.setUserId(dto.getUserId());
        notification.setSalonId(dto.getSalonId());
        notification.setType(dto.getType());
        notification.setDescription(dto.getDescription());
        notification.setRead(dto.isRead());
        notification.setCreatedAt(dto.getCreatedAt());
        return notification;
    }

}
