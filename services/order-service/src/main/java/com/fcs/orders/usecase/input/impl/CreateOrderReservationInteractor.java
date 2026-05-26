package com.fcs.orders.usecase.input.impl;

import com.fcs.orders.entity.Order;
import com.fcs.orders.entity.factory.OrderReservedFactory;
import com.fcs.orders.usecase.gateway.OrderGateway;
import com.fcs.orders.usecase.input.CreateOrderReservationInputBoundary;
import com.fcs.orders.usecase.model.CreateOrderReservationRequestModel;
import com.fcs.orders.usecase.model.CreatedOrderReservationResponseModel;
import com.fcs.orders.usecase.presenter.CreatedOrderReservationPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderReservationInteractor implements CreateOrderReservationInputBoundary {

    private final CreatedOrderReservationPresenter createdOrderReservationPresenter;
    private final OrderGateway orderGateway;

    @Override
    public CreatedOrderReservationResponseModel execute(CreateOrderReservationRequestModel reservationRequestModel) {
        try {
            OrderReservedFactory reservedFactory = new OrderReservedFactory();
            Order createdReservedOrder = reservedFactory.create(reservationRequestModel.eventId(),
                    reservationRequestModel.sessionId(),
                    reservationRequestModel.roomId(),
                    reservationRequestModel.ticketsByChairs(),
                    reservationRequestModel.customerId(),
                    reservationRequestModel.totalPrice());
            if (createdReservedOrder.isValidToReserveOrder()) {
                return createdOrderReservationPresenter.prepareSuccessView(orderGateway.createReservation(createdReservedOrder));
            } else {
                createdOrderReservationPresenter.prepareFailView("Não foi possível criar a reserva do pedido. Verique os dados do pedido.");
            }
        } catch (Exception e) {
            createdOrderReservationPresenter.prepareFailView("Ocorreu um erro interno criar a reserva do pedido. Detalhe: " + e.getMessage());
        }
        return null;
    }
}
