package com.matrix.SHOPPE.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private int id;
    private String username;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
}
