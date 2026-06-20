package com.fcs.admin.usecase.input.impl;

import com.fcs.admin.usecase.gateway.TicketGateway;
import com.fcs.admin.usecase.input.DeleteTicketInputBoundary;
import com.fcs.admin.usecase.presenter.TicketDeletedPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTicketInteractor implements DeleteTicketInputBoundary {

    private final TicketDeletedPresenter ticketDeletedPresenter;
    private final TicketGateway ticketGateway;

    @Override
    public void execute(Integer ticketId) {
        try {
            ticketGateway.delete(ticketId);
        } catch (Exception e) {
            ticketDeletedPresenter.prepareFailView("Ocorreu um erro interno ao excluir o ingresso. Causa: " + e.getMessage());
        }
    }
}
