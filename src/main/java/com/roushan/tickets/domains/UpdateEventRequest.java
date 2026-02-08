package com.roushan.tickets.domains;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.roushan.tickets.domains.entity.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {

    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private List<UpdateTicketTypeRequest> ticketTypes = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public LocalDateTime getSalesStart() {
        return salesStart;
    }

    public void setSalesStart(LocalDateTime salesStart) {
        this.salesStart = salesStart;
    }

    public LocalDateTime getSalesEnd() {
        return salesEnd;
    }

    public void setSalesEnd(LocalDateTime salesEnd) {
        this.salesEnd = salesEnd;
    }

    public EventStatusEnum getStatus() {
        return status;
    }

    public void setStatus(EventStatusEnum status) {
        this.status = status;
    }

    public List<UpdateTicketTypeRequest> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(List<UpdateTicketTypeRequest> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }
}
