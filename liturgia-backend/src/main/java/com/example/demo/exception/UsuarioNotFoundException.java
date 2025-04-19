package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class UsuarioNotFoundException extends CustomException {
    public UsuarioNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
