package com.x.rentacar.model;


import com.x.rentacar.enums.Brands;
import com.x.rentacar.enums.Colors;
import com.x.rentacar.enums.Gear;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "car")
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_id")
    private Long brandId;

    private Brands brand;

    private String model;

    @Enumerated(EnumType.STRING)
    private Colors color;

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
