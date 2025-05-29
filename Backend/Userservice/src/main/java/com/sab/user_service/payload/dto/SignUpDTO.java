package com.sab.user_service.payload.dto;

import lombok.Data;

@Data
public class SignUpDTO {

    private String fullName;
    private String email;
    private String password;
    private String username;
    private Roles role;
}