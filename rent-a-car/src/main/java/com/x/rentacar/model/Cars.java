package com.x.rentacar.model;

import com.x.rentacar.Enums.Colours;
import com.x.rentacar.Enums.Gear;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cars")
@Getter
@Setter
public class Cars {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    private String model;

    @Enumerated(EnumType.STRING)
    private Colours colour;

    @Enumerated(EnumType.STRING)
    private Gear gear;

    private Double price;

    private int year;

    @Column(name = "total_km")
    private int totalKm;

    @Column(name = "units_in_stock")
    private int unitsInStock;

    private Boolean active;

    @Column(name = "product_image_url")
    private String image;

}
