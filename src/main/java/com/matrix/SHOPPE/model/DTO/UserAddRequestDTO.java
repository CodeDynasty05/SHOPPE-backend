package com.matrix.SHOPPE.model.DTO;

import lombok.Data;

@Data
public class UserAddRequestDTO {
    private String name;
    private String email;
    private String password;
    private String phone;
}
