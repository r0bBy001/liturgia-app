package com.example.demo.auth;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // Solo incluye propiedades no nulas en la respuesta JSON
public class AuthResponse {
    private String token;
    private String error;

    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter y setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
