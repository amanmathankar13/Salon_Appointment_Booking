package com.sab.user_service.service.implementation;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sab.user_service.entity.User;
import com.sab.user_service.payload.dto.SignUpDTO;
import com.sab.user_service.payload.response.AuthResponse;
import com.sab.user_service.payload.response.TokenResponse;
import com.sab.user_service.repository.UserRepository;
import com.sab.user_service.service.AuthService;
import com.sab.user_service.service.KeyCloakService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private KeyCloakService keyCloakService;

    @Override
    public AuthResponse login(String username, String password) throws Exception {
        TokenResponse tokenResponse = keyCloakService.getAdminAccessToken(username, password, "password", null);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setRefresh_token(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setMessage("Login Successfully");
        return authResponse;
    }

    @Override
    public AuthResponse signup(SignUpDTO signUpDTO) throws Exception {
        keyCloakService.createUser(signUpDTO);
        User user  = new User();
        user.setUsername(signUpDTO.getUsername());
        user.setPassword(signUpDTO.getPassword());
        user.setEmail(signUpDTO.getEmail());
        user.setRole(signUpDTO.getRole());
        user.setName(signUpDTO.getFullName());
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
        TokenResponse tokenResponse = keyCloakService.getAdminAccessToken(signUpDTO.getUsername(), signUpDTO.getPassword(), "password", null);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setRefresh_token(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setRole(user.getRole());
        authResponse.setMessage("Registered Successfully");
        return authResponse;
    }

    @Override
    public AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception {
        TokenResponse tokenResponse = keyCloakService.getAdminAccessToken(null, null, "refresh_token", refreshToken);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setRefresh_token(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setMessage("Access token recieved Successfully");
        return authResponse;
    }
    
}
