package com.x.rentacar.dto;

import lombok.Data;

@Data
public class OrderCarInfo {

    private Long carId;

    private int quantity;

    private int rentDay;

}
