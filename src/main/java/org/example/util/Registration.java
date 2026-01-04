package org.example.util;

import org.example.model.Customer;

import java.util.Scanner;


public class Registration {
    private final Scanner scanner = new Scanner(System.in);

    public Registration(Customer customer) {

            System.out.println("Please enter your name:");
            customer.setName(scanner.nextLine());

            System.out.println("Please enter your email:");
            customer.setEmail(scanner.nextLine());

            customer.setRegistered(true);
    }
}
