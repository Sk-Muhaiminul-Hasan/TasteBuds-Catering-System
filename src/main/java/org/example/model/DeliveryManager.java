package org.example.model;

import org.example.collections.DriverCollection;
import org.example.collections.OrderCollection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryManager {
    private final OrderCollection orderCollection;
    private final DriverCollection driverCollection;
    private final List<DeliveryVehicle> vehicles;

    public DeliveryManager(OrderCollection orderRepo,
                           DriverCollection driverCollection,
                           List<DeliveryVehicle> vehicles) {
        this.orderCollection = orderRepo;
        this.driverCollection = driverCollection;
        this.vehicles = vehicles;
    }

    public void assignPendingDeliveries() {
        List<Order> allOrders = orderCollection.findAll();
        LocalDateTime currentTime = LocalDateTime.now();

        for (Order order : allOrders) {
            if (order.getStatus() != Order.OrderStatus.READY) {
                continue;
            }



            boolean isPriority = order.isPriority();
            double minutesSinceReady = order.getReadyAt();

            // Priority orders must be assigned within 10 minutes
            if (isPriority && minutesSinceReady > 10) {
                System.out.println("ALERT: Priority order " + order.getOrderId() +
                        " has been waiting for " + minutesSinceReady + " minutes!");
            }

            List<Driver> availableDrivers = driverCollection.findAll().stream()
                    .filter(Driver::isAvailable)
                    .collect(Collectors.toList());

            DeliveryVehicle availableVehicle = vehicles.stream()
                    .filter(DeliveryVehicle::isAvailable)
                    .findFirst()
                    .orElse(null);


            if (!availableDrivers.isEmpty() && availableVehicle != null) {
                Driver driver = availableDrivers.get(0);
                driver.setAvailable(false);
                availableVehicle.setAvailable(false);

                order.setAssignedDriverId(driver.getId());
                order.setStatus(Order.OrderStatus.ASSIGNED);
                orderCollection.save(order);

                String orderType = isPriority ? "PRIORITY" : "normal";
                System.out.println("Assigned " + orderType + " order " + order.getOrderId() +
                        " to driver " + driver.getId());
            }
        }
    }

    public boolean driverCheckout(String orderId, String licenseNumber) {
        Order order = orderCollection.findById(orderId);
        if (order == null || order.getStatus() != Order.OrderStatus.ASSIGNED) {
            return false;
        }

        Driver driver = driverCollection.findByLicenseNumber(licenseNumber);
        if (driver == null || !driver.getId().equals(order.getAssignedDriverId())) {
            return false;
        }

        return true;
    }
}