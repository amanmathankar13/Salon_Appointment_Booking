package com.sab.notification.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/notifications/salon-owner")
public class SalonNotificationController {
    private final NotificationService notificationService;

    private final BookingFeignClient bookingFeignClient;


    // Define your endpoints here

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsBySalonId(@PathVariable("salonId") Long salonId) throws Exception {
        List<Notification> notifications = notificationService.getAllNotificationBySalonId(salonId);
        List<NotificationDTO> notificationDTOs = notifications.stream().map(notification-> {
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
}
