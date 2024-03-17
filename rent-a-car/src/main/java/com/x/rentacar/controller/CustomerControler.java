package com.x.rentacar.controller;

import com.x.rentacar.dto.AuthRequest;
import com.x.rentacar.dto.LoginDto;
import com.x.rentacar.model.Customer;
import com.x.rentacar.service.AuthenticationService;
import com.x.rentacar.service.CustomerService;
import com.x.rentacar.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerControler {

    private final CustomerService customerService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<Customer> signup(@RequestBody Customer customer /* Bu, HTTP isteği içindeki JSON verisini kullanarak bir Customer nesnesi oluşturulmasını sağlar. */){
        return new ResponseEntity<>(customerService.signup(customer), HttpStatus.OK);
    }

    @PostMapping("/addCustomer")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Customer> addUser(@RequestBody Customer customer){
        return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return authenticationService.authenticateAndGetToken(authRequest);
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {


        authenticationService.logout(response);
        return "redirect:/login"; // Oturumdan çıkış yapıldıktan sonra yönlendirilecek sayfa
    }





}
