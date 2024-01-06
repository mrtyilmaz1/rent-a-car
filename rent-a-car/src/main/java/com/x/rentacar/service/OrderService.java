package com.x.rentacar.service;

import com.x.rentacar.repository.CarsRepository;
import com.x.rentacar.repository.OrderDetailRepository;
import com.x.rentacar.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CarsRepository carsRepository;




}
