package com.x.rentacar.service;

import com.x.rentacar.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class UserInfoUserDetailsService {
    private final CustomerRepository customerRepository;

    public UserInfoUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
}
