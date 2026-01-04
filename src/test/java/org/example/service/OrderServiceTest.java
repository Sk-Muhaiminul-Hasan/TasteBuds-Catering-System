package org.example.service;

import org.example.collections.CustomerCollection;
import org.example.model.Customer;
import org.example.model.Item;
import org.example.model.Order;

import org.example.collections.OrderCollection;
import org.example.model.OrderQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderService orderService;
    private CustomerCollection customerCollection;
    private OrderCollection orderCollection;
    private OrderQueue queue;

    @BeforeEach
    void setUp() {
        customerCollection = new CustomerCollection();
        orderCollection = new OrderCollection();
        queue = new OrderQueue();
        orderService = new OrderService(customerCollection, orderCollection, queue);

        Customer registered = new Customer("C1", "Ali", "ali@test.com", true);
        Customer unregistered = new Customer("C2", "Bob", "bob@test.com", false);
        customerCollection.save(registered);
        customerCollection.save(unregistered);
    }

    @Test
    void placeOrder_incrementsOrderCountForRegisteredCustomer() {
        Order order = orderService.placeOrder("C1", Arrays.asList(new Item("Burger", 100.0, 10)));
        assertNotNull(order);
        assertEquals(1, customerCollection.findById("C1").getOrdersThisMonth());
        assertEquals(1, order.getQueueNumber());
    }

    @Test
    void placeOrder_doesNotIncrementForUnregisteredCustomer() {
        Order order = orderService.placeOrder("C2", Arrays.asList(new Item("Fries", 50.0, 20)));
        assertNotNull(order);
        assertEquals(0, customerCollection.findById("C2").getOrdersThisMonth());
    }

    @Test
    void placeOrder_throwsExceptionForUnknownCustomer() {
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.placeOrder("C999", Arrays.asList(new Item("Pizza", 200.0, 15)));
        });
    }
}