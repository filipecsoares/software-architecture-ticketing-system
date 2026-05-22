package com.fcs.tickets.adapter.entrypoint.dto;

public record CreateTicketDto(String name, TicketTypeDto ticketType, TicketUnitPriceDto ticketUnitPrice) {
}
