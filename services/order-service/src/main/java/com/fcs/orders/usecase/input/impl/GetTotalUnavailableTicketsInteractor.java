package com.fcs.orders.usecase.input.impl;

import com.fcs.orders.usecase.gateway.OrderGateway;
import com.fcs.orders.usecase.input.GetTotalUnavailableTicketsInputBoundary;
import com.fcs.orders.usecase.presenter.TotalUnavailableTicketsPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTotalUnavailableTicketsInteractor implements GetTotalUnavailableTicketsInputBoundary {

    private final TotalUnavailableTicketsPresenter totalUnavailableTicketsPresenter;
    private final OrderGateway orderGateway;

    @Override
    public Integer execute(Integer sessionId, Integer ticketId) {
        try {
            return totalUnavailableTicketsPresenter.prepareSuccessView(orderGateway.getTotalUnavailableTicketsForTicketId(sessionId, ticketId));
        } catch (Exception e) {
            totalUnavailableTicketsPresenter.prepareFailView("Ocorreu um erro interno ao obter o total de ingressos ja comprometidos para o ingresso de ID: "
                    + ticketId + ". Detalhe: " + e.getMessage());
        }
        return 0;
    }
}
