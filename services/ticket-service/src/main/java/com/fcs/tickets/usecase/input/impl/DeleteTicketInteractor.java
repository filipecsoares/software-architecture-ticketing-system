package com.fcs.tickets.usecase.input.impl;

import com.fcs.tickets.usecase.gateway.TicketGateway;
import com.fcs.tickets.usecase.input.DeleteTicketInputBoundary;
import com.fcs.tickets.usecase.presenter.TicketDeletedPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTicketInteractor implements DeleteTicketInputBoundary {

    private final TicketDeletedPresenter ticketDeletedPresenter;
    private final TicketGateway ticketGateway;

    @Override
    public void execute(final Integer ticketId) {
        try {
            ticketGateway.delete(ticketId);
        } catch (Exception e) {
            ticketDeletedPresenter.prepareFailView("Ocorreu um erro interno ao excluir o ingresso.");
        }
    }
}
