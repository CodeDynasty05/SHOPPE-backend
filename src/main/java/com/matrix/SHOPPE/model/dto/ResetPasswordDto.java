package com.matrix.SHOPPE.model.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String usernameOrEmail;
    private Integer validationCode;
    private String newPassword;
}
