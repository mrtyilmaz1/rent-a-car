package com.x.rentacar.service;

import com.x.rentacar.repository.CarsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarsService {
    private CarsRepository carsRepository;
}
