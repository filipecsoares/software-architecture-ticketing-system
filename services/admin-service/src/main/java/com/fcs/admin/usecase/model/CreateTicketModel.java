package com.fcs.admin.usecase.model;

public record CreateTicketModel(String name, TicketTypeModel ticketType, TicketUnitPriceModel ticketUnitPrice) {
}
