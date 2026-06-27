package com.fcs.customers.usecase.input.impl;

import com.fcs.customers.usecase.gateway.CustomerGateway;
import com.fcs.customers.usecase.gateway.EventsGateway;
import com.fcs.customers.usecase.gateway.OrdersGateway;
import com.fcs.customers.usecase.input.CreateCustomerOrderReservationInputBoundary;
import com.fcs.customers.usecase.model.CreateCustomerOrderReservationRequestModel;
import com.fcs.customers.usecase.model.EventCustomerResponseModel;
import com.fcs.customers.usecase.presenter.CreateCustomerOrderReservationPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateCustomerOrderReservationInteractor implements CreateCustomerOrderReservationInputBoundary {

    private final EventsGateway eventsGateway;
    private final OrdersGateway ordersGateway;

    private final CreateCustomerOrderReservationPresenter createCustomerOrderReservationPresenter;

    @Override
    public Integer execute(CreateCustomerOrderReservationRequestModel createOrderReservationRequestModel, String customerId) {
        EventCustomerResponseModel eventResponseModel = eventsGateway.getById(createOrderReservationRequestModel.eventId());
        if (eventExists(createOrderReservationRequestModel, eventResponseModel)) {
            boolean allChairsAvailable = true;
            List<String> unavailableChairsToSession = ordersGateway.getUnavailableChairsToSession(createOrderReservationRequestModel.sessionId());
            if (unavailableChairsToSession != null && !unavailableChairsToSession.isEmpty()) {
                allChairsAvailable = createOrderReservationRequestModel
                        .ticketsByChairs()
                        .values()
                        .stream()
                        .flatMap(List::stream)
                        .noneMatch(unavailableChairsToSession::contains);
            }
            if (allChairsAvailable) {
                boolean hasOrderInProgress = ordersGateway.hasOrderInProgress(customerId);
                if (!hasOrderInProgress) {
                    Integer reservationId = ordersGateway.createReservation(createOrderReservationRequestModel.eventId(),
                            createOrderReservationRequestModel.sessionId(),
                            createOrderReservationRequestModel.roomId(),
                            createOrderReservationRequestModel.ticketsByChairs(),
                            customerId,
                            createOrderReservationRequestModel.totalPrice());
                    return createCustomerOrderReservationPresenter.prepareSuccessView(reservationId);
                } else {
                    createCustomerOrderReservationPresenter.prepareFailView("O cliente já possui um pedido em andamento.");
                }
            }  else {
                createCustomerOrderReservationPresenter.prepareFailView("As cadeiras selecionadas não estão mais disponiveis.");
            }
        } else {
            createCustomerOrderReservationPresenter.prepareFailView("Não foi possível validar o evento.");
        }

        return null;
    }

    private boolean eventExists(CreateCustomerOrderReservationRequestModel createOrderReservationRequestModel, EventCustomerResponseModel eventResponseModel) {
        return eventResponseModel != null && eventResponseModel.sessions().stream()
                .anyMatch(session ->
                        session.id().equals(createOrderReservationRequestModel.sessionId()) &&
                                session.roomId().equals(createOrderReservationRequestModel.roomId()) &&
                                session.ticketTypeIdsByQtd().keySet().stream()
                                        .anyMatch(ticketTypeId -> createOrderReservationRequestModel.ticketsByChairs().containsKey(ticketTypeId))
                );
    }
}
