package com.fcs.tickets.usecase.input.impl;

import com.fcs.tickets.usecase.gateway.TicketGateway;
import com.fcs.tickets.usecase.input.GetAllTicketsInputBoundary;
import com.fcs.tickets.usecase.model.TicketResponseModel;
import com.fcs.tickets.usecase.presenter.AllTicketsPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllTicketsInteractor implements GetAllTicketsInputBoundary {

    private final AllTicketsPresenter allTicketsPresenter;
    private final TicketGateway ticketGateway;

    @Override
    public List<TicketResponseModel> execute() {
        List<TicketResponseModel> allTickets = new ArrayList<>();
        try {
            allTickets = ticketGateway.getAll();
        } catch (Exception e) {
            allTicketsPresenter.prepareFailView("Ocorreu um erro interno ao obter todos os ingressos cadastrados.");
        }
        return allTicketsPresenter.prepareSuccessView(allTickets);
    }
}
