package com.allancordeiro.creditanalysis.infrastructure.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestException(BadRequestException ex, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> MethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        StringBuilder messageDetails = new StringBuilder();
        ex.getBindingResult().getAllErrors()
                 .stream()
                 .filter(FieldError.class::isInstance)
                 .map(FieldError.class::cast)
                 .forEach(fieldError -> messageDetails.append(
                         "{'field': '" + fieldError.getField() +
                         "','message': '" + fieldError.getDefaultMessage() + "'},"));

        ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(),
                messageDetails.toString(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        System.out.println(ex);
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

