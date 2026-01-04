package org.example;

import org.example.model.*;
import org.example.collections.*;
import org.example.service.*;
import org.example.util.Registration;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialize collections
        CustomerCollection customerCollection = new CustomerCollection();
        OrderCollection orderCollection = new OrderCollection();
        DriverCollection driverCollection = new DriverCollection();
        FeedbackCollection feedbackCollection = new FeedbackCollection();
        OrderQueue orderQueue = new OrderQueue();
        // Initialize services
        OrderService orderService = new OrderService(customerCollection, orderCollection, orderQueue);
        KitchenService kitchenService = new KitchenService(orderCollection);
        FeedbackService feedbackService = new FeedbackService(orderCollection, feedbackCollection);

        // Create vehicle
        DeliveryVehicle vehicle = new DeliveryVehicle("V1");
        List<DeliveryVehicle> vehicles = Arrays.asList(vehicle);

        // Create driver
        License license = new License("DL-001", "2027-12-31");
        Driver driver = new Driver("D1", "John Driver", license);
        driver.setAvailable(true);
        driverCollection.save(driver);

        // Initialize DeliveryManager
        DeliveryManager deliveryManager = new DeliveryManager(orderCollection, driverCollection, vehicles);

        // Create customer
        Customer customer = new Customer("C1");
        Registration registration = new Registration(customer);
        customer.setOrdersThisMonth(6);
        customerCollection.save(customer);
        System.out.println("Customer created: " + customer.getName());

        // Place order
        Order order = orderService.placeOrder("C1",
                Arrays.asList(new Item("Burger", 10.0, 8.0))
        );
        System.out.println("Order placed: " + order.getOrderId());

        // HeadChef assigns chef
        HeadChef headChef = new HeadChef("HC1", "Head Chef Name");
        Chef chef = new Chef("C1", "Regular Chef");
        chef.setAvailable(true);
        List<Chef> availableChefs = Arrays.asList(chef);

        headChef.determineOrderPriority(order, customer);
        headChef.assignChef(order, availableChefs);
        orderCollection.save(order);

        System.out.println("Chef assigned: " + order.getAssignedChefId());

        // Kitchen prepares order
        kitchenService.prepareOrder(order.getOrderId(), order.getAssignedChefId());
        kitchenService.markReady(order.getOrderId());
        System.out.println("Order prepared and marked as READY");
        System.out.println("Next order: ORD-" + orderQueue.getNextQueueNumber());
        // Assign delivery
        deliveryManager.assignPendingDeliveries();
        System.out.println("Delivery assigned");

        // Driver checkout
        boolean checkoutValid = deliveryManager.driverCheckout(
                order.getOrderId(),
                driver.getLicense().getNumber()
        );
        System.out.println("Driver checkout result: " + checkoutValid);

        // Mark as delivered and submit feedback
        feedbackService.markDelivered(order.getOrderId());
        feedbackService.submitFeedback(order.getOrderId(), 4, "Good service");
        System.out.println("Feedback submitted");
    }
}