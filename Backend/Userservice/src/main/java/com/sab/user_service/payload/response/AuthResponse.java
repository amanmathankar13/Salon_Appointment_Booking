package com.sab.user_service.payload.response;


import com.sab.user_service.payload.dto.Roles;

import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String refresh_token;
    private String message;
    private String title;
    private Roles role;
}
