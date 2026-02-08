package com.roushan.tickets.services;

import com.roushan.tickets.domains.entity.QrCode;
import com.roushan.tickets.domains.entity.Ticket;

import java.util.UUID;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
