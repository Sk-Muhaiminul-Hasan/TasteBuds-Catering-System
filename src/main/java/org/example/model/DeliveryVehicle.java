package org.example.model;

public class DeliveryVehicle {
    private String id;
    private boolean available;

    public DeliveryVehicle(String id) {
        this.id = id;
        this.available = true;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}