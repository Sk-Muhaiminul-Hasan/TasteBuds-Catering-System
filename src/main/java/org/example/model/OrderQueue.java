package org.example.model;

import java.util.concurrent.atomic.AtomicInteger;

public class OrderQueue implements CurrentOrderQueue {
    private final AtomicInteger nextNumber = new AtomicInteger(1);
    private final AtomicInteger currentServing = new AtomicInteger(0);

    @Override
    public int getNextQueueNumber() {
        return nextNumber.getAndIncrement();
    }

    @Override
    public int getCurrentServingNumber() {
        return currentServing.get();
    }

    @Override
    public void serveNext() {
        int current = currentServing.get();
        int next = nextNumber.get() - 1;
        if (current < next) {
            currentServing.incrementAndGet();
        }
    }
}