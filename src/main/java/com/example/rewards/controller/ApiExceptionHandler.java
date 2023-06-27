package com.example.rewards.controller;

import com.example.rewards.exception.CustomerCreationException;
import com.example.rewards.exception.CustomerNotFoundException;
import com.example.rewards.exception.TransactionNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            CustomerNotFoundException.class,
            TransactionNotFoundException.class
    })
    public ResponseEntity<Void> notFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({
            CustomerCreationException.class
    })
    public ResponseEntity<Void> badRequest() {
        return ResponseEntity.badRequest().build();
    }

}
