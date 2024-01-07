package com.x.rentacar.controller;

import com.x.rentacar.dto.OrderRequest;
import com.x.rentacar.model.Order;
import com.x.rentacar.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Order> doOrder(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.doOrder(orderRequest), HttpStatus.OK);
    }




}
