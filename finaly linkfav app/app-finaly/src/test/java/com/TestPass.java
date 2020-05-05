package com;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestPass {
    @Test
    public void getPass(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println( passwordEncoder.encode("$2a$10$XEcUbF4S2m6IlgAgyRgD2ejQjFwLFWLzY8Y0Ivb9NdvLKLK6On8GW"));
    }
}
