package com.fcs.tickets.usecase.input.impl;

import com.fcs.tickets.entity.Ticket;
import com.fcs.tickets.usecase.gateway.TicketGateway;
import com.fcs.tickets.usecase.input.CreateTicketInputBoundary;
import com.fcs.tickets.usecase.model.TicketCreatedResponseModel;
import com.fcs.tickets.usecase.presenter.TicketCreatedPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTicketInteractor implements CreateTicketInputBoundary {

    private final TicketGateway ticketGateway;
    private final TicketCreatedPresenter ticketCreatedPresenter;

    @Override
    public TicketCreatedResponseModel execute(final Ticket toCreate) {
        if (!toCreate.isValid())
            ticketCreatedPresenter.prepareFailView("Verifique que todas as informações do ingresso estão preenchidas.");
        boolean ticketAlreadyExists = ticketGateway.exists(toCreate.getName());
        if (ticketAlreadyExists)
            ticketCreatedPresenter.prepareFailView("Já existe um ingresso cadastrado com o nome '" + toCreate.getName() + "'");
        Integer createdId = ticketGateway.create(toCreate);
        toCreate.setId(createdId);
        return ticketCreatedPresenter.prepareSuccessView(toCreate);
    }
}
