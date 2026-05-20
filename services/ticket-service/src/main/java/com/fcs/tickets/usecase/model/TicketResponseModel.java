package com.fcs.tickets.usecase.model;

public record TicketResponseModel(Integer id, String name, TicketTypeResponseModel type, TicketUnitPriceResponseModel unitPrice) {
}
