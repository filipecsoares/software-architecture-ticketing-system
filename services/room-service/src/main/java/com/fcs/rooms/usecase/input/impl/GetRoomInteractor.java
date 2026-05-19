package com.fcs.rooms.usecase.input.impl;

import com.fcs.rooms.usecase.gateway.RoomGateway;
import com.fcs.rooms.usecase.input.GetRoomInputBoundary;
import com.fcs.rooms.usecase.model.RoomResponseModel;
import com.fcs.rooms.usecase.presenter.RoomPresenter;
import org.springframework.stereotype.Service;

@Service
public class GetRoomInteractor implements GetRoomInputBoundary {

    private final RoomPresenter roomPresenter;
    private final RoomGateway roomGateway;

    public GetRoomInteractor(final RoomPresenter roomPresenter, final RoomGateway roomGateway) {
        this.roomPresenter = roomPresenter;
        this.roomGateway = roomGateway;
    }

    @Override
    public RoomResponseModel execute(final Integer roomId) {
        try {
            return roomPresenter.prepareSuccessView(roomGateway.getById(roomId));
        } catch (Exception e) {
            roomPresenter.prepareFailView("Ocorreu um erro interno ao obter os dados da sala.");
        }
        return null;
    }
}
