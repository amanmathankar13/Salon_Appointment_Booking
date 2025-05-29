package com.sab.user_service.payload.dto;

import lombok.Data;

@Data
public class Credentials {
    private String type;
    private String value;
    private boolean temporary;
}
