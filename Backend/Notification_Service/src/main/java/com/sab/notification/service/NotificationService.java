package com.sab.notification.service;

import java.util.List;

import com.sab.notification.entity.Notification;
import com.sab.notification.payload.dto.NotificationDTO;

public interface NotificationService {

   NotificationDTO createNotification(Notification notification) throws Exception;

   List<Notification> getAllNotificationByUserId(Long userId);
   List<Notification> getAllNotificationBySalonId(Long salonId);
   Notification markNotificationAsRead(Long notificationId);
}
