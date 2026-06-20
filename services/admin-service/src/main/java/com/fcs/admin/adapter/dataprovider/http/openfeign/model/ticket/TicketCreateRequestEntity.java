package com.fcs.admin.adapter.dataprovider.http.openfeign.model.ticket;

import com.fcs.admin.usecase.model.TicketTypeModel;
import com.fcs.admin.usecase.model.TicketUnitPriceModel;

public record TicketCreateRequestEntity(String name, TicketTypeModel ticketType, TicketUnitPriceModel ticketUnitPrice) {
}
