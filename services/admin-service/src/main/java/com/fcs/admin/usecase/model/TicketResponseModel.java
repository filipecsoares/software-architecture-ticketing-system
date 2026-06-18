package com.fcs.admin.usecase.model;

public record TicketResponseModel(Integer id, String name, TicketTypeResponseModel type, TicketUnitPriceResponseModel unitPrice) {
}
