package com.sab.user_service.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HomeController {

    @GetMapping("/")
    public String homeController() {
        return new String("User Microservice for salon appointment booking system");
    }
    
}
