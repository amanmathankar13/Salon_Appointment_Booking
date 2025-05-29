package com.sab.user_service.payload.dto;

import java.util.Map;

import lombok.Data;

@Data
public class KeyCloakRole {
    private String id;
    private String description;
    private String name;
    private Boolean composite;
    private Boolean clientRole;
    private String containerId;
    private Map<String, Object> attributes;
}
