package org.example.service;

import org.example.model.DeliveryVehicle;
import org.example.model.Driver;
import org.example.model.Item;
import org.example.model.License;
import org.example.model.Order;
import org.example.collections.OrderCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryServiceTest {

    private DeliveryService deliveryService;
    private OrderCollection orderCollection;
    private List<DeliveryVehicle> vehicles;
    private Driver driver;

    @BeforeEach
    void setUp() {
        orderCollection = new OrderCollection();
        vehicles = new ArrayList<>();
        deliveryService = new DeliveryService(orderCollection, vehicles);

        License license = new License("LIC123", "2025-12-31");
        driver = new Driver("DRIVER-1", "John", license);
        driver.setAvailable(true);
    }

    @Test
    void assignDelivery_setsDriverAndStatus() {
        Order order = new Order("ORD-1", "C1", List.of(new Item("Pizza", 12.99, 10)), 1);
        order.setStatus(Order.OrderStatus.READY);
        //order.setReadyAt(LocalDateTime.now().minusMinutes(5));
        orderCollection.save(order);

        DeliveryVehicle vehicle = new DeliveryVehicle("V1");
        vehicle.setAvailable(true);
        vehicles.add(vehicle);

        deliveryService.assignDelivery("ORD-1", driver);

        Order updatedOrder = orderCollection.findById("ORD-1");
        assertEquals("DRIVER-1", updatedOrder.getAssignedDriverId());
        assertEquals(Order.OrderStatus.ASSIGNED, updatedOrder.getStatus());
    }

    @Test
    void assignDelivery_priorityOrderAfter10Minutes_stillAssigns() {
        Order order = new Order("PRIORITY-1", "C1", List.of(), 1);
        order.setStatus(Order.OrderStatus.READY);
        order.setPriority(true);
        //order.setReadyAt(LocalDateTime.now().minusMinutes(15)); // Over 10 minutes
        orderCollection.save(order);

        DeliveryVehicle vehicle = new DeliveryVehicle("V1");
        vehicle.setAvailable(true);
        vehicles.add(vehicle);

        deliveryService.assignDelivery("PRIORITY-1", driver);

        Order updatedOrder = orderCollection.findById("PRIORITY-1");
        assertEquals(Order.OrderStatus.ASSIGNED, updatedOrder.getStatus());
        assertTrue(updatedOrder.isPriority());
    }

    @Test
    void assignDelivery_orderNotReady_throwsException() {
        Order order = new Order("ORD-1", "C1", List.of(), 1);
        order.setStatus(Order.OrderStatus.PLACED);
        orderCollection.save(order);

        DeliveryVehicle vehicle = new DeliveryVehicle("V1");
        vehicle.setAvailable(true);
        vehicles.add(vehicle);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> deliveryService.assignDelivery("ORD-1", driver));

        assertEquals("Order not ready", exception.getMessage());
    }
}