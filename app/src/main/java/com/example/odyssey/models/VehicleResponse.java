package com.example.odyssey.models;

import java.util.List;

public class VehicleResponse {
    private String status;
    private String message;
    private VehicleModel vehicle;

    public VehicleResponse(String status, String message, VehicleModel vehicle) {
        this.status = status;
        this.message = message;
        this.vehicle = vehicle;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public VehicleModel getData() {
        return vehicle;
    }
}
