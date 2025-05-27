package com.matrix.SHOPPE.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResetPasswordDto {
    @NotNull(message = "Username or Email must not be null")
    private String usernameOrEmail;
    @NotNull(message = "Validation Code must not be null")
    private Integer validationCode;
    @NotNull(message = "New Password must not be null")
    private String newPassword;
}
