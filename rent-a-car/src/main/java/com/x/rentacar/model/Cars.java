package com.x.rentacar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Table(name = "cars")
@Getter
@Setter
public class Cars {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String brand;

    private String model;

    private Double price;

    @Column(name = "units_in_stock")
    private Long unitsInStock;

    private Boolean active;

    @Column(name = "product_image_url")
    private String image;

}
