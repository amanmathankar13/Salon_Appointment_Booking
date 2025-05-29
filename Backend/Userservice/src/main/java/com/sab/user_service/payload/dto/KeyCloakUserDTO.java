package com.sab.user_service.payload.dto;

import lombok.Data;

@Data
public class KeyCloakUserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
}
