package com.allancordeiro.creditanalysis.infrastructure.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbidenException extends Exception{
    private static final long serialVersionUID = 1L;

    public ForbidenException(Exception ex) {
        super(ex.getMessage());
    }
}
