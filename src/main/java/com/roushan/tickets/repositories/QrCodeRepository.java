package com.roushan.tickets.repositories;

import java.util.Optional;
import java.util.UUID;

import com.roushan.tickets.domains.entity.QrCode;
import com.roushan.tickets.domains.entity.QrCodeStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {
    Optional<QrCode> findByTicketIdAndTicketPurchaserId(UUID ticketId, UUID ticketPurchaseId);
    Optional<QrCode> findByIdAndStatus(UUID id, QrCodeStatusEnum status);
}
