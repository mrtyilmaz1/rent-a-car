package com.x.rentacar.service;

import com.x.rentacar.enums.Roles;
import com.x.rentacar.model.Customer;
import com.x.rentacar.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    public Customer addCustomer(Customer customer){
        // Rol null ise otomatik olar user tanımlamak için.
        if (Objects.isNull(customer.getRoles())){
            customer.setRoles(Roles.ROLE_USER);
        }

        if (customerRepository.existsByEmail (customer.getEmail())) { // existsByEmail ilgili kaydın var olup olmadığını kontrol eder.
            log.error("Bu mail adresi zaten kullanılmaktadır.");
            throw new RuntimeException("Bu mail adresi zaten kullanılmaktadır.");
        }

        // şifreyi kodlayarak saklamak için.
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    public Customer signUp(Customer customer){

        if (Objects.isNull(customer.getRoles()) || customer.getRoles().equals(Roles.ROLE_ADMIN) ){
            customer.setRoles(Roles.ROLE_USER);
        }

        if (customerRepository.existsByEmail (customer.getEmail())) {
            log.error("Bu mail adresi zaten kullanılmaktadır.");
            throw new RuntimeException("Bu mail adresi zaten kullanılmaktadır.");
        }

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }



}
