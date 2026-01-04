package org.example.model;

import java.util.Collection;
import java.util.List;

public class Order {

    public enum OrderStatus {
        PLACED, PREPARING, READY, ASSIGNED, DELIVERED, RATED
    }

    private String orderId;
    private String customerId;
    private List<Item> items;
    private int queueNumber;
    private OrderStatus status;
    private boolean priority; // ← just a boolean
    private String assignedChefId;
    private String assignedDriverId;
    private double readyAt;

    public Order(String orderId, String customerId, List<Item> items, int queueNumber) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.queueNumber = queueNumber;
        this.status = OrderStatus.PLACED;
        this.priority = false; // default
        this.estimatedOrderTime();
    }

    // Getters & Setters
    public String getOrderId() { return orderId; }
    public int getQueueNumber() { return queueNumber; }
    public OrderStatus getStatus() { return status; }
    public String getCustomerId() {return customerId;}
    public List<Item> getItems() {return items;}
    public String getAssignedDriverId() {return assignedDriverId;}
    public String getAssignedChefId() {return assignedChefId;}
    public double getReadyAt() {return readyAt;}

    public void setAssignedChefId(String assignedChefId) {this.assignedChefId = assignedChefId;}
    public void setStatus(OrderStatus status) { this.status = status; }
    public boolean isPriority() { return priority; }
    public void setPriority(boolean priority) { this.priority = priority; }
    public void setAssignedDriverId(String driverId) { this.assignedDriverId = driverId; }
    public void setReadyAt(double readyAt) {this.readyAt = readyAt;}

    public void estimatedOrderTime(){
        double maxTime = 0;
        for (Item i : items) {
            if(maxTime < i.getTimeTakenToPrepare())
                maxTime = i.getTimeTakenToPrepare();
        }
        setReadyAt(maxTime);
    }
}