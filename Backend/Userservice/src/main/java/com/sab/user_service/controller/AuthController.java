package com.sab.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sab.user_service.payload.dto.LoginDTO;
import com.sab.user_service.payload.dto.SignUpDTO;
import com.sab.user_service.payload.response.AuthResponse;
import com.sab.user_service.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignUpDTO signUpDTO) throws Exception {
        return ResponseEntity.ok(authService.signup(signUpDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDTO) throws Exception {
        return ResponseEntity.ok(authService.login(loginDTO.getEmail(),loginDTO.getPassword()));
    }

    @GetMapping("/access-token/refresh-token/{refreshToken}")
    public ResponseEntity<AuthResponse> getAccessToken(@PathVariable String refreshToken) throws Exception {
        return ResponseEntity.ok(authService.getAccessTokenFromRefreshToken(refreshToken));
    }
    
    

}
