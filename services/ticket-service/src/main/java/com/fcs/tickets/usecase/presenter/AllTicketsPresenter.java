package com.fcs.tickets.usecase.presenter;

import com.fcs.tickets.usecase.model.TicketResponseModel;

import java.util.List;

public interface AllTicketsPresenter {

    List<TicketResponseModel> prepareSuccessView(final List<TicketResponseModel> allTickets);

    void prepareFailView(final String error);
}
