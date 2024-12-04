package com.example.odyssey.models;

public class PaymentRequest {
    private String amount;

    public PaymentRequest(String amount) {
        this.amount = amount;
    }

    // Getter and Setter
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
