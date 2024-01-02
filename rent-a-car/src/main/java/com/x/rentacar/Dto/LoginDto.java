package com.x.rentacar.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String token;
    private Long customerId;
}
