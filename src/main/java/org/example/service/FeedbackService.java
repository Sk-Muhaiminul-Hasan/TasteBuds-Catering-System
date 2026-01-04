package org.example.service;

import org.example.model.Order;
import org.example.collections.FeedbackCollection;
import org.example.collections.OrderCollection;

public class FeedbackService {
    private final OrderCollection orderRepo;
    private final FeedbackCollection feedbackRepo;

    public FeedbackService(OrderCollection orderRepo, FeedbackCollection feedbackRepo) {
        this.orderRepo = orderRepo;
        this.feedbackRepo = feedbackRepo;
    }

    public void markDelivered(String orderId) {
        Order order = orderRepo.findById(orderId);
        order.setStatus(Order.OrderStatus.DELIVERED);
        orderRepo.save(order);
    }

    public void submitFeedback(String orderId, int rating, String comment) {
        Order order = orderRepo.findById(orderId);
        if (order.getStatus() != Order.OrderStatus.DELIVERED) {
            throw new IllegalStateException("Order not delivered");
        }
        feedbackRepo.save(orderId, rating, comment);
        order.setStatus(Order.OrderStatus.RATED);
        orderRepo.save(order);
    }
}