package com.example.oop.service;

import com.example.oop.model.Ticket;
import com.example.oop.repository.TicketRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    private final TicketRepo repository;

    @Autowired
    public TicketService(TicketRepo repository) {
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
        Optional<Ticket> ticket = repository.findById(id);

        if (ticket.isPresent()) {
            throw new Exception("Ticket with ID " + id + " not found");
        }

        if (ticket.get().isSold()) {
            throw new Exception("Ticket with ID " + id + " is already sold");
        }

        ticket.get().setSold(true);
        repository.save(ticket.get());

        logger.info("Ticket with ID {} sold successfully", id);

        return ticket.get();


    }
}