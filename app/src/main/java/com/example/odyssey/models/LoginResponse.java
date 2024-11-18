package com.example.odyssey.models;

public class LoginResponse {
    private String status;
    private String message;
    private String token;

    public String isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}