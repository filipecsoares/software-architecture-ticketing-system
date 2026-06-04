package com.fcs.customers.usecase.input.impl;

import com.fcs.customers.usecase.gateway.EventsGateway;
import com.fcs.customers.usecase.gateway.OrdersGateway;
import com.fcs.customers.usecase.input.ListEventsToCustomerInputBoundary;
import com.fcs.customers.usecase.model.EventCustomerResponseModel;
import com.fcs.customers.usecase.presenter.EventsToCustomerPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListEventsToCustomerInteractor implements ListEventsToCustomerInputBoundary {

    private final EventsToCustomerPresenter eventsToCustomerPresenter;
    private final EventsGateway eventsGateway;
    private final OrdersGateway ordersGateway;

    @Override
    public List<EventCustomerResponseModel> execute() {
        try {
            List<EventCustomerResponseModel> eventCustomerResponseModels = eventsToCustomerPresenter.prepareSuccessView(eventsGateway.get());
            adjustOriginalTicketsTotalAvailableWithOrdersReservedOrBought(eventCustomerResponseModels);
            return eventCustomerResponseModels;
        } catch (Exception e) {
            eventsToCustomerPresenter.prepareFailView("Ocorreu um erro interno ao obter os eventos para o cliente. Detalhe: " + e.getMessage());
        }
        return null;
    }

    private void adjustOriginalTicketsTotalAvailableWithOrdersReservedOrBought(List<EventCustomerResponseModel> eventCustomerResponseModels) {
        if (eventCustomerResponseModels != null && !eventCustomerResponseModels.isEmpty()) {
            eventCustomerResponseModels.forEach(eventCustomerResponseModel -> {
                eventCustomerResponseModel.sessions().forEach(session -> {
                    session.ticketTypeIdsByQtd().forEach((ticketId, total) -> {
                        Integer unavailableTickets = ordersGateway.getUnavailableTicketsToSession(session.id(), ticketId);
                        session.ticketTypeIdsByQtd().put(ticketId, total - unavailableTickets);
                    });
                });
            } );
        }
    }
}
