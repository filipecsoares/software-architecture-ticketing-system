package com.fcs.tickets.usecase.input.impl;

import com.fcs.tickets.usecase.gateway.TicketGateway;
import com.fcs.tickets.usecase.input.GetTicketInputBoundary;
import com.fcs.tickets.usecase.model.TicketResponseModel;
import com.fcs.tickets.usecase.presenter.TicketPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTicketInteractor implements GetTicketInputBoundary {

    private final TicketPresenter ticketPresenter;
    private final TicketGateway ticketGateway;

    @Override
    public TicketResponseModel execute(final Integer ticketId) {
        try {
            return ticketPresenter.prepareSuccessView(ticketGateway.getById(ticketId));
        } catch (Exception e) {
            ticketPresenter.prepareFailView("Ocorreu um erro interno ao obter os dados do ingresso.");
            return null;
        }
    }
}
