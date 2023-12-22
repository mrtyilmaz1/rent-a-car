package com.x.rentacar.model;

//TODO: Burada country, city, district gibi field'ları başka bir api'dan
// restTemplate ile çekebiliriz.
// ? direkt javascript ile yapabilir miyiz? spring boot'dan işlem yapmadan

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    private String country;

    private String city;

    private String district;

    private String postCode;

    @Column(name = "address_line")
    private String addressLine;
}
