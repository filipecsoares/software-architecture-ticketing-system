package com.fcs.admin.adapter.dataprovider.http.openfeign.model.ticket;

public record TicketResponseEntity(Integer id, String name, TicketTypeResponseEntity type, TicketUnitPriceResponseEntity unitPrice) {
}
