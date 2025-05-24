package com.matrix.SHOPPE.model.dto;

import lombok.Data;

@Data
public class UserAddRequestDto {
    private String username;
    private String email;
    private String password;
    private String phone;
}
