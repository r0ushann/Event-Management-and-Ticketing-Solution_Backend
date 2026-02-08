package com.roushan.tickets.services.impl;

import com.roushan.tickets.domains.entity.*;
import com.roushan.tickets.exceptions.QrCodeNotFoundException;
import com.roushan.tickets.exceptions.TicketNotFoundException;
import com.roushan.tickets.repositories.QrCodeRepository;
import com.roushan.tickets.repositories.TicketRepository;
import com.roushan.tickets.repositories.TicketValidationRepository;
import com.roushan.tickets.services.TicketValidationService;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationServiceImpl implements TicketValidationService {

    private final QrCodeRepository qrCodeRepository;
    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;

    public TicketValidationServiceImpl(QrCodeRepository qrCodeRepository, TicketValidationRepository ticketValidationRepository, TicketRepository ticketRepository) {
        this.qrCodeRepository = qrCodeRepository;
        this.ticketValidationRepository = ticketValidationRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
                .orElseThrow(() -> new QrCodeNotFoundException(
                        String.format(
                                "QR Code with ID %s was not found", qrCodeId
                        )
                ));

        Ticket ticket = qrCode.getTicket();

        return validateTicket(ticket, TicketValidationMethod.QR_SCAN);
    }

    private TicketValidation validateTicket(Ticket ticket,
                                            TicketValidationMethod ticketValidationMethod) {
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(ticketValidationMethod);

        TicketValidationStatusEnum ticketValidationStatus = ticket.getValidations().stream()
                .filter(v -> TicketValidationStatusEnum.VALID.equals(v.getStatus()))
                .findFirst()
                .map(v -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatus);

        return ticketValidationRepository.save(ticketValidation);
    }

    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketNotFoundException::new);
        return validateTicket(ticket, TicketValidationMethod.MANUAL);
    }
}
