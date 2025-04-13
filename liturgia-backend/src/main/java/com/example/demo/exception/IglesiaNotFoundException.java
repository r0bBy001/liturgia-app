package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class IglesiaNotFoundException extends CustomException {
    public IglesiaNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
