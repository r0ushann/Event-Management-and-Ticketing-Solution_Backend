package com.roushan.tickets.repositories;

import java.util.UUID;

import com.roushan.tickets.domains.entity.TicketValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketValidationRepository extends JpaRepository<TicketValidation, UUID> {
}
