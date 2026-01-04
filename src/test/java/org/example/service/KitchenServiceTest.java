package org.example.service;

import org.example.model.Item;
import org.example.model.Order;
import org.example.collections.OrderCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KitchenServiceTest {

    private KitchenService kitchenService;
    private OrderCollection orderCollection;

    @BeforeEach
    void setUp() {
        orderCollection = new OrderCollection();
        kitchenService = new KitchenService(orderCollection);

        // Create an order that already has a chef assigned (by HeadChef)
        Item pizza = new Item("Pizza", 12.99, 15.0);
        Item salad = new Item("Salad", 8.99, 5.0);
        Order order = new Order("ORD-1", "C1", List.of(pizza, salad), 1);
        order.setAssignedChefId("Chef_A"); // Already assigned by HeadChef
        orderCollection.save(order);
    }

    @Test
    void prepareOrder_startsPreparation() {
        // Act - KitchenService starts preparation with assigned chef
        kitchenService.prepareOrder("ORD-1", "Chef_A");

        // Assert
        Order order = orderCollection.findById("ORD-1");
        assertEquals(Order.OrderStatus.PREPARING, order.getStatus());
        assertEquals("Chef_A", order.getAssignedChefId());
    }

    @Test
    void markReady_completesPreparation() {
        // Arrange - First prepare the order
        kitchenService.prepareOrder("ORD-1", "Chef_A");

        // Act - Then mark as ready
        kitchenService.markReady("ORD-1");

        // Assert
        Order order = orderCollection.findById("ORD-1");
        assertEquals(Order.OrderStatus.READY, order.getStatus());
        // readyAt should be calculated based on items (15.0 minutes for pizza)
        assertEquals(15.0, order.getReadyAt(), 0.001);
    }

    @Test
    void prepareOrder_orderNotFound_throwsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> kitchenService.prepareOrder("NON_EXISTENT", "Chef_A")
        );
        assertEquals("Order not found: NON_EXISTENT", exception.getMessage());
    }

    @Test
    void prepareOrder_withDifferentChef_updatesChef() {
        // Arrange - Order already has Chef_A assigned by HeadChef

        // Act - KitchenService can update if needed
        kitchenService.prepareOrder("ORD-1", "Chef_B");

        // Assert
        Order order = orderCollection.findById("ORD-1");
        assertEquals("Chef_B", order.getAssignedChefId());
        assertEquals(Order.OrderStatus.PREPARING, order.getStatus());
    }

    @Test
    void markReady_orderNotPreparing_stillWorks() {
        // Arrange - Order is PLACED (not PREPARING)
        Order order = orderCollection.findById("ORD-1");
        assertEquals(Order.OrderStatus.PLACED, order.getStatus());

        // Act - Try to mark as ready without preparing
        kitchenService.markReady("ORD-1");

        // Assert - Should still work (allows flexibility)
        order = orderCollection.findById("ORD-1");
        assertEquals(Order.OrderStatus.READY, order.getStatus());
    }
}