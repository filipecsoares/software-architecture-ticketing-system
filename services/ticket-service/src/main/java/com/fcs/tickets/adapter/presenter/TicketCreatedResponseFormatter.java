package com.fcs.tickets.adapter.presenter;

import com.fcs.tickets.entity.Ticket;
import com.fcs.tickets.usecase.exception.BusinessException;
import com.fcs.tickets.usecase.model.TicketCreatedResponseModel;
import com.fcs.tickets.usecase.presenter.TicketCreatedPresenter;
import org.springframework.stereotype.Component;

@Component
public class TicketCreatedResponseFormatter implements TicketCreatedPresenter {

    @Override
    public TicketCreatedResponseModel prepareSuccessView(Ticket ticket) {
        return new TicketCreatedResponseModel(ticket.getId());
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
