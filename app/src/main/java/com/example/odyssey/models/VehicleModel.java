package com.example.odyssey.models;

public class VehicleModel {
    private int vehicle_id;
    private int driver_id;
    private String type;
    private String license_plate_number;
    private double mileage;
    private int number_of_seats;
    private String chasis_number;
    private String model;
    private int year;
    private String color;
    private String owner_mobile_number;
    private String owner_image;
    private String main_image;
    private String front_image;
    private String back_image;
    private String left_image;
    private String interior_image;
    private String right_image;

    public VehicleModel(int vehicle_id, int driver_id, String type, String license_plate_number, double mileage, int number_of_seats, String chasis_number, String model, int year, String color, String owner_mobile_number, String owner_image, String main_image, String front_image, String back_image, String left_image, String interior_image, String right_image) {
        this.vehicle_id = vehicle_id;
        this.driver_id = driver_id;
        this.type = type;
        this.license_plate_number = license_plate_number;
        this.mileage = mileage;
        this.number_of_seats = number_of_seats;
        this.chasis_number = chasis_number;
        this.model = model;
        this.year = year;
        this.color = color;
        this.owner_mobile_number = owner_mobile_number;
        this.owner_image = owner_image;
        this.main_image = main_image;
        this.front_image = front_image;
        this.back_image = back_image;
        this.left_image = left_image;
        this.interior_image = interior_image;
        this.right_image = right_image;
    }


    public int getVehicle_id() {
        return vehicle_id;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public String getType() {
        return type;
    }

    public String getLicense_plate_number() {
        return license_plate_number;
    }

    public double getMileage() {
        return mileage;
    }

    public int getNumber_of_seats() {
        return number_of_seats;
    }

    public String getChasis_number() {
        return chasis_number;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getOwner_mobile_number() {
        return owner_mobile_number;
    }

    public String getOwner_image() {
        return owner_image;
    }

    public String getMain_image() {
        return main_image;
    }

    public String getFront_image() {
        return front_image;
    }

    public String getBack_image() {
        return back_image;
    }

    public String getLeft_image() {
        return left_image;
    }

    public String getInterior_image() {
        return interior_image;
    }

    public String getRight_image() {
        return right_image;
    }
}
