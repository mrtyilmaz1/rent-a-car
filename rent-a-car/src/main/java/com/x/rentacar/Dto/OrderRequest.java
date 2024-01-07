package com.x.rentacar.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private Long customerId;

    private List<OrderCarInfo> orderCarInfoList;
}
