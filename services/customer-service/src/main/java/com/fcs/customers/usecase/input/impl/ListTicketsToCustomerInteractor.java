package com.fcs.customers.usecase.input.impl;

import com.fcs.customers.usecase.gateway.TicketsGateway;
import com.fcs.customers.usecase.input.ListTicketsToCustomerInputBoundary;
import com.fcs.customers.usecase.model.TicketCustomerResponseModel;
import com.fcs.customers.usecase.presenter.TicketsToCustomerPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListTicketsToCustomerInteractor implements ListTicketsToCustomerInputBoundary {

    private final TicketsToCustomerPresenter ticketsToCustomerPresenter;
    private final TicketsGateway ticketsGateway;

    @Override
    public List<TicketCustomerResponseModel> execute(List<Integer> ticketsIds) {
        try {
            return ticketsToCustomerPresenter.prepareSuccessView(ticketsGateway.getBy(ticketsIds));
        } catch (Exception e) {
            ticketsToCustomerPresenter.prepareFailView("Ocorreu um erro interno ao obter os ingressos para o cliente. Detalhe: " + e.getMessage());
        }
        return null;
    }
}
