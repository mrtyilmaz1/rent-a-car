package com.x.rentacar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Test {

    private final PasswordEncoder passwordEncoder;
    @GetMapping("/{password}")
    public String encode(@PathVariable(value = "password") String password) {
        return passwordEncoder.encode(password);
    }
}
