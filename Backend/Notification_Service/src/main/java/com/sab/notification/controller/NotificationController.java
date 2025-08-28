package com.sab.notification.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sab.notification.entity.Notification;
import com.sab.notification.mapper.NotificationMapper;
import com.sab.notification.payload.dto.BookingDTO;
import com.sab.notification.payload.dto.NotificationDTO;
import com.sab.notification.service.NotificationService;
import com.sab.notification.service.client.BookingFeignClient;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    private final BookingFeignClient bookingFeignClient;


    // Define your endpoints here
    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody Notification notification) throws Exception {
        NotificationDTO createdNotification = notificationService.createNotification(notification);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable("userId") Long userId) throws Exception {
        List<Notification> notifications = notificationService.getAllNotificationByUserId(userId);
        List<NotificationDTO>  notificationDTOs = notifications.stream().map(notification-> {
            BookingDTO booking = null;
            try {
                booking = bookingFeignClient.getBookingById(notification.getBookingId()).getBody();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return NotificationMapper.toDTO(notification, booking);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(notificationDTOs, HttpStatus.OK);
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<NotificationDTO> markNotificationAsRead(@PathVariable("notificationId") Long notificationId) throws Exception {
        Notification updatedNotification = notificationService.markNotificationAsRead(notificationId);
        return new ResponseEntity<>(NotificationMapper.toDTO(updatedNotification, bookingFeignClient.getBookingById(updatedNotification.getBookingId()).getBody()), HttpStatus.OK);
    }
}
