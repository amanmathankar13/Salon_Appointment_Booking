package com.sab.user_service.service;

import com.sab.user_service.payload.dto.SignUpDTO;
import com.sab.user_service.payload.response.AuthResponse;

public interface AuthService{

    AuthResponse login(String username, String password) throws Exception;
    AuthResponse signup(SignUpDTO signUpDTO) throws Exception;
    AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception;
}
