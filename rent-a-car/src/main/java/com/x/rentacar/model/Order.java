package com.x.rentacar.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "rent_day")
    private int rentDay;

    @Column(name = "order_started_date")
    private LocalDateTime orderStartedDate;

    @Column(name = "order_finished_date")
    private LocalDateTime orderFinishedDate;


}
