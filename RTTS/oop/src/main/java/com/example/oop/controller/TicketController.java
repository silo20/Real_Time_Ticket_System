package com.example.oop.controller;

import com.example.oop.model.Ticket;
import com.example.oop.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private TicketService service;

    @PostMapping
    public Ticket addTicket(@RequestParam String eventName) {
        return service.addTicket(eventName);
    }

    @GetMapping
    public List<Ticket> getTickets() {
        return service.getTickets();
    }

    @PostMapping("/{id}/sell")
    public Ticket sellTicket(@PathVariable Long id) throws Exception {
        return service.sellTicket(id);
    }
}

