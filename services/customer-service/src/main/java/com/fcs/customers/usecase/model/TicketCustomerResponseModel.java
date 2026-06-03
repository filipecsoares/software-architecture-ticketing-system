package com.fcs.customers.usecase.model;

public record TicketCustomerResponseModel(Integer id, String name, TicketTypeResponseModel type, TicketUnitPriceResponseModel unitPrice) {
}
