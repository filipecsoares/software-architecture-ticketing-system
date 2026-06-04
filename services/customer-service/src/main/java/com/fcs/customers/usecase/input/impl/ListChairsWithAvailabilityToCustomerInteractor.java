package com.fcs.customers.usecase.input.impl;

import com.fcs.customers.usecase.gateway.OrdersGateway;
import com.fcs.customers.usecase.gateway.RoomsGateway;
import com.fcs.customers.usecase.input.ListChairsWithAvailabilityToCustomerInputBoundary;
import com.fcs.customers.usecase.model.RoomDetailsWithChairsAvailabilityToCustomerResponseModel;
import com.fcs.customers.usecase.model.SeatResponseModel;
import com.fcs.customers.usecase.presenter.ChairsWithAvailabilityToCustomerPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListChairsWithAvailabilityToCustomerInteractor implements ListChairsWithAvailabilityToCustomerInputBoundary {

    private final ChairsWithAvailabilityToCustomerPresenter chairsWithAvailabilityToCustomerPresenter;
    private final RoomsGateway roomsGateway;
    private final OrdersGateway ordersGateway;

    @Override
    public RoomDetailsWithChairsAvailabilityToCustomerResponseModel execute(Integer roomId, Integer sessionId) {
        try {
            RoomDetailsWithChairsAvailabilityToCustomerResponseModel roomDetailsWithChairsAvailabilityToCustomerResponseModel
                    = roomsGateway.getRoomById(roomId);
            List<String> unavailableChairs = ordersGateway.getUnavailableChairsToSession(sessionId);

            if (roomDetailsWithChairsAvailabilityToCustomerResponseModel == null) {
                chairsWithAvailabilityToCustomerPresenter.prepareFailView("Nao foi possivel validar a sala de ID '" + roomId);
            }

            if (roomDetailsWithChairsAvailabilityToCustomerResponseModel.getSeats() == null ||
                    roomDetailsWithChairsAvailabilityToCustomerResponseModel.getSeats().isEmpty()) {
                chairsWithAvailabilityToCustomerPresenter.prepareFailView("Nao foi possivel encontrar cadeiras para a sala de ID '" + roomId);
            }

            List<SeatResponseModel> seatsWithAvailability = roomDetailsWithChairsAvailabilityToCustomerResponseModel.getSeats().stream()
                    .peek(seat -> {
                        if (unavailableChairs != null && !unavailableChairs.isEmpty() &&
                                unavailableChairs.contains(seat.getName())) {
                            seat.setAvailable(false);
                        }
                    }).toList();
            roomDetailsWithChairsAvailabilityToCustomerResponseModel.setSeats(seatsWithAvailability);

            return chairsWithAvailabilityToCustomerPresenter.prepareSuccessView(roomDetailsWithChairsAvailabilityToCustomerResponseModel);
        } catch (Exception e) {
            chairsWithAvailabilityToCustomerPresenter.prepareFailView("Ocorreu um erro interno ao obter as cadeiras da sala ID '" + roomId + "' alocada para a sessao ID '" + sessionId + "' + Detalhe: " + e.getMessage());
        }
        return null;
    }
}
