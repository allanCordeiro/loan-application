package com.allancordeiro.creditanalysis.infrastructure.security.login.exceptions;

public class UnauthorizedException extends Exception {
    public UnauthorizedException() {
        super("Invalid login attempt");
    }
}
