package com.sab.user_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sab.user_service.entity.User;
import com.sab.user_service.repository.UserRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping("/get")
    public User getUser() {
       User user = new User();
       user.setName("Aman");
       user.setEmail("mathankaraman13@gmail.com");
       user.setPhoneNumber("1234567890");
       user.setCreatedAt(LocalDateTime.now());
       return user;
    }
    

    @PostMapping("/login")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
}
