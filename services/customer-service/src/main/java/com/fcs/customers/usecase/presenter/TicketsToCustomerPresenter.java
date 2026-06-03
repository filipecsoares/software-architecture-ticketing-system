package com.fcs.customers.usecase.presenter;

import com.fcs.customers.usecase.model.TicketCustomerResponseModel;

import java.util.List;

public interface TicketsToCustomerPresenter {
    List<TicketCustomerResponseModel> prepareSuccessView(List<TicketCustomerResponseModel> ticketCustomerResponseModels);

    void prepareFailView(String error);
}
