package com.matrix.SHOPPE.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String createdAt;
}
