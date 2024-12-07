package com.example.oop.service;

import com.example.oop.model.Ticket;
import com.example.oop.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepo repository;

    // Constructor for Spring Dependency Injection
    @Autowired
    public TicketService(int repository) {
        this.repository = repository;
    }

    // Add a new ticket with the specified event name
    public Ticket addTicket(String eventName) {
        Ticket ticket = new Ticket(eventName);
        return repository.save(ticket);
    }

    // Retrieve all tickets
    public List<Ticket> getTickets() {
        return repository.findAll();
    }

    // Sell a ticket by ID
    public Ticket sellTicket(Long id) throws Exception {
        // Find the ticket by ID
        Optional<Ticket> optionalTicket = repository.findById(id);

        if (optionalTicket.isEmpty()) {
            throw new Exception("Ticket not found");
        }

        Ticket ticket = optionalTicket.get();

        // Check if the ticket is already sold
        if (ticket.isSold()) {
            throw new Exception("Ticket already sold");
        }

        // Mark the ticket as sold
        ticket.setSold(true);
        return repository.save(ticket);
    }
}

