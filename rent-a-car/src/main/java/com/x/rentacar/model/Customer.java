package com.x.rentacar.model;

import com.x.rentacar.Enums.Roles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "first_name")
    private String firstName;

    private String surname;

    private String password;

    private Address address;

    private Roles roles;

}
