package com.fcs.tickets.usecase.presenter;

import com.fcs.tickets.usecase.model.TicketResponseModel;

public interface TicketPresenter {

    TicketResponseModel prepareSuccessView(final TicketResponseModel ticketResponseModel);

    void prepareFailView(final String error);
}
