package org.example.service;

import org.example.model.Order;
import org.example.collections.FeedbackCollection;
import org.example.collections.OrderCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackServiceTest {

    private FeedbackService feedbackService;
    private OrderCollection orderCollection;
    private FeedbackCollection feedbackCollection;

    @BeforeEach
    void setUp() {
        orderCollection = new OrderCollection();
        feedbackCollection = new FeedbackCollection();
        feedbackService = new FeedbackService(orderCollection, feedbackCollection);

        Order order = new Order("F1", "C1", java.util.List.of(), 1);
        order.setStatus(Order.OrderStatus.DELIVERED);
        orderCollection.save(order);
    }

    @Test
    void submitFeedback_updatesOrderToRated() {
        feedbackService.submitFeedback("F1", 5, "Great!");

        Order order = orderCollection.findById("F1");
        assertEquals(Order.OrderStatus.RATED, order.getStatus());
        //assertTrue(feedbackRepo.hasFeedback("F1"));
    }

    @Test
    void submitFeedback_onNonDeliveredOrder_throwsException() {
        Order notDelivered = new Order("F2", "C1", java.util.List.of(), 1);
        notDelivered.setStatus(Order.OrderStatus.READY);
        orderCollection.save(notDelivered);

        assertThrows(IllegalStateException.class, () -> {
            feedbackService.submitFeedback("F2", 4, "OK");
        });
    }
}