package com.x.rentacar.dataInitializer;

import com.x.rentacar.model.OrderDetail;
import com.x.rentacar.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailDataInitializer implements CommandLineRunner {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    public void run(String... args) throws Exception {
        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setId(1L); orderDetail1.setOrderId(1L);
        orderDetail1.setCarId(1L); orderDetail1.setPrice(525000.0);
        orderDetail1.setRentDay(2); orderDetail1.setQuantity(3);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setId(2L); orderDetail2.setOrderId(2L);
        orderDetail2.setCarId(2L); orderDetail2.setPrice(650000.0);
        orderDetail2.setRentDay(1); orderDetail2.setQuantity(2);

        orderDetailRepository.save(orderDetail1);
        orderDetailRepository.save(orderDetail2);
    }
}
