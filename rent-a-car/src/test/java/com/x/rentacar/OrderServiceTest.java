package com.x.rentacar;

import com.x.rentacar.dto.OrderCarInfo;
import com.x.rentacar.dto.OrderRequest;
import com.x.rentacar.model.Car;
import com.x.rentacar.model.Order;
import com.x.rentacar.model.OrderDetail;
import com.x.rentacar.repository.CarRepository;
import com.x.rentacar.repository.OrderDetailRepository;
import com.x.rentacar.repository.OrderRepository;
import com.x.rentacar.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void doOrder() {
        // Arrange
        OrderRequest orderRequest = createTestOrderRequest();
        when(carRepository.findById(any())).thenReturn(Optional.of(createTestCar()));
        when(orderRepository.save(any(Order.class))).thenReturn(createTestOrder());
        when(orderDetailRepository.save(any(OrderDetail.class))).thenReturn(createTestOrderDetail());

        // Act
        Order result = orderService.doOrder(orderRequest);

        // Assert
        assertNotNull(result);
        assertEquals(orderRequest.getCustomerId(), result.getCustomerId());
        assertEquals(orderRequest.getOrderCarInfoList().get(0).getRentDay(), result.getRentDay());
    }

    @Test
    void carUnitStockCheck() {
        // Arrange
        OrderCarInfo orderCarInfo = new OrderCarInfo();
        orderCarInfo.setCarId(1L);
        orderCarInfo.setQuantity(2);

        when(carRepository.findById(orderCarInfo.getCarId())).thenReturn(Optional.of(createTestCar()));

        // Act and Assert
        assertDoesNotThrow(() -> orderService.carUnitStockCheck(Collections.singletonList(orderCarInfo)));

    }


    private OrderRequest createTestOrderRequest() {
        // Create and return a sample OrderRequest object for testing
        OrderCarInfo orderCarInfo = new OrderCarInfo();
        orderCarInfo.setCarId(1L);
        orderCarInfo.setQuantity(2);
        orderCarInfo.setRentDay(3);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerId(1L);
        orderRequest.setOrderCarInfoList(Collections.singletonList(orderCarInfo));
        return orderRequest;
    }

    private Car createTestCar() {
        // Create and return a sample Car object for testing
        Car car = new Car();
        car.setId(1L);
        car.setUnitsInStock(5);
        car.setPrice(100.0);
        return car;
    }

    private Order createTestOrder() {
        // Create and return a sample Order object for testing
        Order order = new Order();
        order.setId(1L);
        order.setCustomerId(1L);
        order.setRentDay(3);
        order.setOrderStartedDate(LocalDateTime.now());
        order.setOrderFinishedDate(LocalDateTime.now().plusDays(3));
        return order;
    }

    private OrderDetail createTestOrderDetail() {
        // Create and return a sample OrderDetail object for testing
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(1L);
        orderDetail.setOrderId(1L);
        orderDetail.setCarId(1L);
        orderDetail.setRentDay(3);
        orderDetail.setQuantity(2);
        orderDetail.setTotalPrice(200.0);
        return orderDetail;
    }
}

