package org.example.model;

public interface CurrentOrderQueue {
    int getNextQueueNumber();
    int getCurrentServingNumber();
    void serveNext();
}