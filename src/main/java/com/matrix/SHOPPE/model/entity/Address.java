package com.matrix.SHOPPE.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name="addresses")
public class Address {
    @Id
    private Integer id;
    private String address;
    private String city;
    private String postalCode;
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

}
