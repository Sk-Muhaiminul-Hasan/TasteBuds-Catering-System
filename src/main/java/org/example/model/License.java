package org.example.model;

public class License {
    private String number;
    private String expiryDate;

    public License(String number, String expiryDate) {
        this.number = number;
        this.expiryDate = expiryDate;
    }

    public String getNumber() { return number; }
    public String getExpiryDate() { return expiryDate; }
}