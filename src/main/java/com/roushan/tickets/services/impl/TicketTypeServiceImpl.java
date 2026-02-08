package com.roushan.tickets.services.impl;

import com.roushan.tickets.domains.entity.Ticket;
import com.roushan.tickets.domains.entity.TicketStatusEnum;
import com.roushan.tickets.domains.entity.TicketType;
import com.roushan.tickets.domains.entity.User;
import com.roushan.tickets.exceptions.TicketTypeNotFoundException;
import com.roushan.tickets.exceptions.TicketsSoldOutException;
import com.roushan.tickets.exceptions.UserNotFoundException;
import com.roushan.tickets.repositories.TicketRepository;
import com.roushan.tickets.repositories.TicketTypeRepository;
import com.roushan.tickets.repositories.UserRepository;
import com.roushan.tickets.services.QrCodeService;
import com.roushan.tickets.services.TicketTypeService;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;

    public TicketTypeServiceImpl(UserRepository userRepository, TicketTypeRepository ticketTypeRepository, TicketRepository ticketRepository, QrCodeService qrCodeService) {
        this.userRepository = userRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.ticketRepository = ticketRepository;
        this.qrCodeService = qrCodeService;
    }

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                String.format("User with ID %s was not found", userId)
        ));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
                .orElseThrow(() -> new TicketTypeNotFoundException(
                        String.format("Ticket type with ID %s was not found", ticketTypeId)
                ));

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();

        if(purchasedTickets + 1 > totalAvailable) {
            throw new TicketsSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);
    }
}