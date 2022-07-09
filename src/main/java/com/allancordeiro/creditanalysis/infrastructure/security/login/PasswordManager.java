package com.allancordeiro.creditanalysis.infrastructure.security.login;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;


public class PasswordManager {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordManager() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder(
                10,
                new SecureRandom()
        );
    }

    public String encodePassword(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    public Boolean match(String encodedPassword, String rawPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
