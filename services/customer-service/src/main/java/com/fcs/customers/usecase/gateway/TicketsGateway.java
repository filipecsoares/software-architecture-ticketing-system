package com.fcs.customers.usecase.gateway;

import com.fcs.customers.usecase.model.TicketCustomerResponseModel;

import java.util.List;

public interface TicketsGateway {
    List<TicketCustomerResponseModel> getBy(List<Integer> ticketsIds);
}
