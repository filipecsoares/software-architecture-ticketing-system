package com.fcs.orders.usecase.input.impl;

import com.fcs.orders.usecase.gateway.OrderGateway;
import com.fcs.orders.usecase.input.GetOrderReservationDetailsInputBoundary;
import com.fcs.orders.usecase.model.OrderReservationDetailsResponseModel;
import com.fcs.orders.usecase.presenter.OrderReservationDetailsPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrderReservationDetailsInteractor implements GetOrderReservationDetailsInputBoundary {

    private final OrderGateway gateway;
    private final OrderReservationDetailsPresenter presenter;

    @Override
    public OrderReservationDetailsResponseModel execute(Integer reservationId) {
        try {
            return presenter.prepareSuccessView(gateway.getOrderReservationDetails(reservationId));
        } catch (Exception e) {
            presenter.prepareFailView("Ocorreu um erro interno ao obter os detalhes da reserva. Detalhe: " + e.getMessage());
        }
        return null;
    }
}
