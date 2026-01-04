package org.example.service;

import org.example.model.DeliveryVehicle;
import org.example.model.Driver;
import org.example.model.Order;
import org.example.collections.OrderCollection;

import java.util.List;

public class DeliveryService {
    private final OrderCollection orderRepo;
    private final List<DeliveryVehicle> vehicles;

    public DeliveryService(OrderCollection orderRepo, List<DeliveryVehicle> vehicles) {
        this.orderRepo = orderRepo;
        this.vehicles = vehicles;
    }

    public void assignDelivery(String orderId, Driver driver) {
        Order order = orderRepo.findById(orderId);
        if (order == null || order.getStatus() != Order.OrderStatus.READY) {
            throw new IllegalStateException("Order not ready");
        }

        // Validate driver license already done externally

        DeliveryVehicle vehicle = vehicles.stream()
                .filter(DeliveryVehicle::isAvailable)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No vehicle available"));

        vehicle.setAvailable(false);
        driver.setAvailable(false);

        order.setAssignedDriverId(driver.getId());
        order.setStatus(Order.OrderStatus.ASSIGNED);
        orderRepo.save(order);
    }
}