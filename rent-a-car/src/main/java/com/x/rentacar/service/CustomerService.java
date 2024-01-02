package com.x.rentacar.service;

import com.x.rentacar.enums.Roles;
import com.x.rentacar.model.Customer;
import com.x.rentacar.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Customer addCustomer(Customer customer){
        if (Objects.isNull(customer.getRoles())){
            customer.setRoles(Roles.ROLE_USER);
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }




}
