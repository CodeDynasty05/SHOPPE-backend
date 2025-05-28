package com.matrix.SHOPPE.model.dto;

import lombok.Data;

@Data
public class RegisterResponseDto {
    private final String username;
    private final String email;
    private String jwtToken;
}
