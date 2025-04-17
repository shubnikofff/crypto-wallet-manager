package com.shubnikofff.crypto_wallet_manager.controller;


import com.shubnikofff.crypto_wallet_manager.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handle(IllegalArgumentException e) {
        log.warn(e.getMessage());

        return ResponseEntity
            .badRequest()
            .body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handle(NotFoundException e) {
        log.warn(e.getMessage());

        return ResponseEntity.notFound().build();
    }

}
