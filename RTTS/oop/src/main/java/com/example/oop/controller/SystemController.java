package com.example.oop.controller;

import com.example.oop.model.Configuration;
import com.example.oop.model.Ticket;
import com.example.oop.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")  // Base URL for ticket-related actions
public class SystemController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private Configuration configuration;

    // Endpoint to create a new ticket
    @PostMapping("/create")
    public Ticket createTicket(@RequestParam String eventName) {
        // Create a ticket with a specified event name
        return ticketService.addTicket(eventName);
    }

    // Endpoint to retrieve all tickets
    @GetMapping("/all")
    public List<Ticket> getAllTickets() {
        // Get all tickets from the database
        return ticketService.getTickets();
    }

    // Endpoint to sell a ticket (mark as sold)
    @PostMapping("/sell/{id}")
    public Ticket sellTicket(@PathVariable Long id) throws Exception {
        // Sell the ticket by ID
        return ticketService.sellTicket(id);
    }

    // Endpoint to get configuration settings
    @GetMapping("/configuration")
    public Configuration getConfiguration() {
        // Retrieve the configuration object
        return configuration;
    }

    // Endpoint to update configuration settings
    @PutMapping("/configuration")
    public Configuration updateConfiguration(@RequestBody Configuration newConfig) {
        // Update configuration values based on the input from the client
        configuration.setTotalTickets(newConfig.getTotalTickets());
        configuration.setTicketReleaseRate(newConfig.getTicketReleaseRate());
        configuration.setCustomerRetrievalRate(newConfig.getCustomerRetrievalRate());
        configuration.setMaxTicketCapacity(newConfig.getMaxTicketCapacity());

        // Optionally save the configuration back to the file or database
        configuration.saveToFile("config.json");

        return configuration;
    }
}