package com.x.rentacar.service;

import com.x.rentacar.dto.OrderCarInfo;
import com.x.rentacar.dto.OrderRequest;
import com.x.rentacar.model.Car;
import com.x.rentacar.model.Order;
import com.x.rentacar.model.OrderDetail;
import com.x.rentacar.repository.CarRepository;
import com.x.rentacar.repository.OrderDetailRepository;
import com.x.rentacar.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CarRepository carRepository;

    private void carUnitStockCheck(List<OrderCarInfo> orderCarInfoList) {

        orderCarInfoList.forEach(carInfo -> {
            Long carStock = Long.valueOf(carRepository.findById(carInfo.getCarId())
                    .map(Car::getUnitsInStock)
                    .orElseThrow(() -> new RuntimeException("Ürün bulunamadı" + carInfo.getCarId())));

            if (carStock - carInfo.getQuantity() < 0) {
                log.error("Araba sayısı yeterli değill. id: {} adet: {}", carInfo.getCarId() , carInfo.getQuantity());
                throw new RuntimeException("İstenilen araç sayısı stoktaki araç sayısından fazladır.");
            }
        });
    }

    public Order doOrder(OrderRequest orderRequest) {
        log.info("Order isteği geldi. time: {} customer: {}", LocalDateTime.now(), orderRequest.getCustomerId());

        carUnitStockCheck(orderRequest.getOrderCarInfoList());
        Order order = new Order();
        OrderCarInfo orderCarInfo = new OrderCarInfo();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setOrderStartedDate(LocalDateTime.now());
        order.setRentDay(orderCarInfo.getRentDay());
        order.setOrderFinishedDate((LocalDateTime.now().plusDays(orderCarInfo.getRentDay())));
        Order ordered = orderRepository.save(order);

        orderRequest.getOrderCarInfoList().forEach(e -> {
            Optional<Car> car = carRepository.findById(e.getCarId());

            if (car.isPresent()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setTotalPrice(car.get().getPrice() * ordered.getRentDay());
                orderDetail.setQuantity(e.getQuantity());
                orderDetail.setCarId(e.getCarId());
                orderDetail.setOrderId(ordered.getId());
                orderDetail.setRentDay(ordered.getRentDay());
                orderDetailRepository.save(orderDetail);
                int lasUnitStock = car.get().getUnitsInStock();
                if (lasUnitStock - e.getQuantity() == 0) {
                    car.get().setActive(false);
                }
                car.get().setUnitsInStock(lasUnitStock - e.getQuantity());
                carRepository.save(car.get());
            }

        });
        return ordered;
    }




}
