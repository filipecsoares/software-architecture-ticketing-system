package com.fcs.tickets.usecase.presenter;

import com.fcs.tickets.entity.Ticket;
import com.fcs.tickets.usecase.model.TicketCreatedResponseModel;

public interface TicketCreatedPresenter {

    TicketCreatedResponseModel prepareSuccessView(final Ticket ticket);

    void prepareFailView(final String error);
}
