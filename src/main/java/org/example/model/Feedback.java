package org.example.model;

import java.time.LocalDateTime;

public class Feedback {
    private String feedbackId;
    private String orderId;
    private String customerId;
    private int rating; // 1-5
    private String comments;
    private LocalDateTime feedbackDate;

    public Feedback(String feedbackId, String orderId, String customerId) {
        this.feedbackId = feedbackId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.feedbackDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getFeedbackId() { return feedbackId; }
    public void setFeedbackId(String feedbackId) { this.feedbackId = feedbackId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public int getRating() { return rating; }
    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        }
    }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public LocalDateTime getFeedbackDate() { return feedbackDate; }
    public void setFeedbackDate(LocalDateTime feedbackDate) { this.feedbackDate = feedbackDate; }
}