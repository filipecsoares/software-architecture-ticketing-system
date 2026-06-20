package com.fcs.admin.usecase.input.impl;

import com.fcs.admin.usecase.gateway.TicketGateway;
import com.fcs.admin.usecase.input.GetAllTicketsInputBoundary;
import com.fcs.admin.usecase.model.TicketResponseModel;
import com.fcs.admin.usecase.presenter.AllTicketsPresenter;
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
            allTicketsPresenter.prepareFailView("Ocorreu um erro interno ao obter todos os ingressos cadastrados. Causa: " + e.getMessage());
        }
        return allTicketsPresenter.prepareSuccessView(allTickets);
    }
}
