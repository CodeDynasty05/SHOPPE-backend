package com.matrix.SHOPPE.model.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String usernameOrEmail;
    private String password;
}
