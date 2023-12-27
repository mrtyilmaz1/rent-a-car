package com.x.rentacar.DataInitializer;

import com.x.rentacar.model.Order;
import com.x.rentacar.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderDataInitializer implements CommandLineRunner {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        Order order1 = new Order();
        order1.setId(1L); order1.setCustomerId(1L);
        order1.setRentDay(2); order1.setOrderStartedDate(LocalDateTime.now());
        order1.setOrderFinishedDate(LocalDateTime.now().plusDays(2));

        Order order2 = new Order();
        order2.setId(2L); order2.setCustomerId(2L);
        order2.setRentDay(1); order2.setOrderStartedDate(LocalDateTime.now());
        order2.setOrderFinishedDate(LocalDateTime.now().plusDays(1));

        orderRepository.save(order1);
        orderRepository.save(order2);

    }
}
