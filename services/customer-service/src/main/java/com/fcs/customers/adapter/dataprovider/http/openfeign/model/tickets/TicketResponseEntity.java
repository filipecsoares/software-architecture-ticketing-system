package com.fcs.customers.adapter.dataprovider.http.openfeign.model.tickets;

public record TicketResponseEntity(Integer id, String name, TicketTypeResponseEntity type, TicketUnitPriceResponseEntity unitPrice) {
}
