package com.matrix.SHOPPE.model.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String createdAt;
}
