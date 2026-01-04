package org.example.service;

import org.example.model.Order;
import org.example.collections.OrderCollection;

public class KitchenService {
    private final OrderCollection orderCollection;

    public KitchenService(OrderCollection orderCollection) {
        this.orderCollection = orderCollection;
    }

    public void prepareOrder(String orderId, String chefId) {
        Order order = orderCollection.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        order.setAssignedChefId(chefId);
        order.setStatus(Order.OrderStatus.PREPARING);
        orderCollection.save(order);
    }

    public void markReady(String orderId) {
        Order order = orderCollection.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        order.setStatus(Order.OrderStatus.READY);
        orderCollection.save(order);
    }
}