package com.example.oop.controller;

import com.example.oop.model.Configuration;
import com.example.oop.service.Customer;
import com.example.oop.service.TicketService;
import com.example.oop.service.Vendor;

import java.util.ArrayList;
import java.util.List;

public class SystemController {
    private Configuration configuration; // System configuration
    private TicketService ticketService;       // Shared ticket pool
    private List<Thread> vendorThreads;  // List of vendor threads
    private List<Thread> customerThreads; // List of customer threads

    public SystemController() {
        this.vendorThreads = new ArrayList<>();
        this.customerThreads = new ArrayList<>();
    }

    // Initialize the system with a given configuration
    public void initializeSystem(Configuration configuration) {
        this.configuration = configuration;
        this.ticketService = new TicketService(configuration.getMaxTicketCapacity());
        System.out.println("System initialized with configuration:");
        System.out.println("Total Tickets: " + configuration.getTotalTickets());
        System.out.println("Max Ticket Capacity: " + configuration.getMaxTicketCapacity());
        System.out.println("Ticket Release Rate: " + configuration.getTicketReleaseRate());
        System.out.println("Customer Retrieval Rate: " + configuration.getCustomerRetrievalRate());
    }

    // Start the vendors (producers)
    public void startVendors(int numberOfVendors) {
        for (int i = 0; i < numberOfVendors; i++) {
            Vendor vendor = new Vendor(ticketService, configuration.getTicketReleaseRate());
            Thread vendorThread = new Thread(vendor, "Vendor-" + (i + 1));
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }
        System.out.println(numberOfVendors + " vendors started.");
    }

    // Start the customers (consumers)
    public void startCustomers(int numberOfCustomers) {
        for (int i = 0; i < numberOfCustomers; i++) {
            Customer customer = new Customer(ticketService, configuration.getCustomerRetrievalRate());
            Thread customerThread = new Thread(customer, "Customer-" + (i + 1));
            customerThreads.add(customerThread);
            customerThread.start();
        }
        System.out.println(numberOfCustomers + " customers started.");
    }

    // Stop all threads gracefully
    public void stopSystem() {
        // Interrupt all vendor threads
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }
        // Interrupt all customer threads
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }
        System.out.println("System stopped gracefully.");
    }

    // Save the current configuration to a file
    public void saveConfiguration(String fileName) {
        if (configuration != null) {
            configuration.saveToFile(fileName);
            System.out.println("Configuration saved to file: " + fileName);
        } else {
            System.out.println("No configuration to save.");
        }
    }

    // Load configuration from a file
    public void loadConfiguration(String fileName) {
        Configuration loadedConfig = Configuration.loadFromFile(fileName);
        if (loadedConfig != null) {
            initializeSystem(loadedConfig);
            System.out.println("Configuration loaded from file: " + fileName);
        } else {
            System.out.println("Failed to load configuration.");
        }
    }
}
