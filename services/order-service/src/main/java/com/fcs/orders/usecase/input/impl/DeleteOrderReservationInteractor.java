package com.fcs.orders.usecase.input.impl;

import com.fcs.orders.usecase.gateway.OrderGateway;
import com.fcs.orders.usecase.input.DeleteOrderReservationInputBoundary;
import com.fcs.orders.usecase.presenter.DeletedOrderReservationPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteOrderReservationInteractor implements DeleteOrderReservationInputBoundary {

    private final DeletedOrderReservationPresenter deletedOrderReservationPresenter;
    private final OrderGateway orderGateway;

    @Override
    public void execute(Integer reservationId) {
        try {
            orderGateway.deleteReservation(reservationId);
        } catch (Exception e) {
            deletedOrderReservationPresenter.prepareFailView("Ocorreu um erro ao deletar a reserva do pedido. Detalhe: " + e.getMessage());
        }
    }
}
