package com.sab.notification.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sab.notification.entity.Notification;
import com.sab.notification.mapper.NotificationMapper;
import com.sab.notification.payload.dto.BookingDTO;
import com.sab.notification.payload.dto.NotificationDTO;
import com.sab.notification.repository.NotificationRepository;
import com.sab.notification.service.NotificationService;
import com.sab.notification.service.client.BookingFeignClient;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final BookingFeignClient bookingFeignClient;

    @Override
    public NotificationDTO createNotification(Notification notification) throws Exception {
        Notification savedNotification = notificationRepository.save(notification);
        BookingDTO bookingDTO = bookingFeignClient.getBookingById(notification.getBookingId()).getBody();
        return NotificationMapper.toDTO(savedNotification, bookingDTO);
    }

    @Override
    public List<Notification> getAllNotificationByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getAllNotificationBySalonId(Long salonId) {
        return notificationRepository.findBySalonId(salonId);
    }

    @Override
    public Notification markNotificationAsRead(Long notificationId) {
        return notificationRepository.findById(notificationId).map(notification -> {
            notification.setRead(true);
            return notificationRepository.save(notification);
        }).orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));
    }
}
