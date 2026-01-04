package org.example.model;

public class Customer {
    private String id;
    private String name;
    private String email;
    private boolean registered;
    private int ordersThisMonth;

    public Customer(String id){
        this.id = id;
        this.registered = false;
        this.ordersThisMonth = 0;
    }

    public Customer(String id, String name, String email, boolean registered) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.registered = registered;
        this.ordersThisMonth = 0;
    }

    public double calculateDiscount(double total) {
        if (!registered) return 0.0;
        if (ordersThisMonth >= 10) return total * 0.15;
        if (ordersThisMonth >= 5) return total * 0.10;
        return total * 0.05;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isRegistered() { return registered; }
    public int getOrdersThisMonth() { return ordersThisMonth; }
    public void incrementOrders() { ordersThisMonth++; }
    //Setters
    public void setId(String id) {this.id = id;}
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setOrdersThisMonth(int ordersThisMonth) {this.ordersThisMonth = ordersThisMonth;}
    public void setRegistered(boolean registered) {this.registered = registered;}
}