package com.fcs.customers.usecase.input;

import com.fcs.customers.usecase.model.TicketCustomerResponseModel;

import java.util.List;

public interface ListTicketsToCustomerInputBoundary {
    List<TicketCustomerResponseModel> execute(List<Integer> ticketsIds);
}
