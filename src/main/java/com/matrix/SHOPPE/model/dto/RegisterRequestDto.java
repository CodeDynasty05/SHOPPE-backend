package com.matrix.SHOPPE.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequestDto {
    @NotNull(message = "Username or Email must not be null")
    private String usernameOrEmail;
    @NotNull(message = "Password must not be null")
    private String password;
}
