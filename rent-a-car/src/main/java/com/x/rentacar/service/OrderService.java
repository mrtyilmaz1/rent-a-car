package com.x.rentacar.service;

import com.x.rentacar.repository.CarsRepository;
import com.x.rentacar.repository.OrderDetailRepository;
import com.x.rentacar.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
