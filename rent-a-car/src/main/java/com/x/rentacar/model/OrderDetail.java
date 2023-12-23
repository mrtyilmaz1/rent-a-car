package com.x.rentacar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

    @Entity
    @Table(name = "order_details")
    @Getter
    @Setter
    public class OrderDetail {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "order_id")
        private Long orderId;

        @Column(name = "car_id")
        private Long carId;

        private Double price;

        @Column(name = "rent_day")
        private int rentDay;

    }
