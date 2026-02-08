package com.roushan.tickets.services;

import com.roushan.tickets.domains.entity.Ticket;

import java.util.UUID;

public interface TicketTypeService {
    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}
