package com.x.rentacar.Dto;

import lombok.Data;

@Data
public class LoginDto {
    private String token;
    private Long customerId;
}
