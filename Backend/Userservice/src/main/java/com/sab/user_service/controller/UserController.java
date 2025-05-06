package com.sab.user_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sab.user_service.entity.User;
import com.sab.user_service.exception.UserException;
import com.sab.user_service.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public User createUser(@RequestBody @Valid User user){
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @GetMapping("/get")
    public List<User> getUser() {
       return userRepository.findAll();
    }
    

    // @PostMapping("/login")
    // public String postMethodName(@RequestBody String entity) {
    //     //TODO: process POST request
        
    //     return entity;
    // }


    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(()-> new UserException("User Not Found"));
    }


    @PutMapping("/update/{id}")
    public User putMethodName(@PathVariable Long id, @RequestBody User user) throws Exception {
        User existingUser = userRepository.findById(id).orElse(null); 
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setRole(user.getRole());
            existingUser.setUpdatedAt(LocalDateTime.now());
            userRepository.save(existingUser);
            return existingUser;
        }
        throw new UserException("User Not Found");
    }
    

    @DeleteMapping("/delete/{id}")
    public String deleteMethodName(@PathVariable Long id) throws Exception {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            userRepository.delete(existingUser);
            return "User Deleted";
        }
        throw new UserException("User Not Found");
    }
}
