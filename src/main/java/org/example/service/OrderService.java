package org.example.service;

import org.example.model.*;
import org.example.collections.CustomerCollection;
import org.example.collections.OrderCollection;
import org.example.model.OrderQueue;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderService {
    private final CustomerCollection customerCollection;
    private final OrderCollection orderCollection;
    private final AtomicInteger queueCounter = new AtomicInteger(1);
    private final OrderQueue queue;

    public OrderService(CustomerCollection customerCollection, OrderCollection orderCollection, OrderQueue queue) {
        this.customerCollection = customerCollection;
        this.orderCollection = orderCollection;
        this.queue = queue;
    }

    public Order placeOrder(String customerId, List<Item> items) {
        Customer customer = customerCollection.findById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        int queueNum = queueCounter.getAndIncrement();
        String orderId = "ORD-" + queueNum;
        int qNum = queue.getNextQueueNumber();
        Order order = new Order(orderId, customerId, items, qNum);

        if (customer.isRegistered()) {
            customer.incrementOrders();
            customerCollection.save(customer);
        }

        orderCollection.save(order);
        return order;
    }
}