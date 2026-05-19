package com.fcs.rooms.usecase.input.impl;

import com.fcs.rooms.usecase.gateway.RoomGateway;
import com.fcs.rooms.usecase.input.DeleteRoomInputBoundary;
import com.fcs.rooms.usecase.presenter.RoomDeletedPresenter;
import org.springframework.stereotype.Service;

@Service
public class DeleteRoomInteractor implements DeleteRoomInputBoundary {

    private final RoomDeletedPresenter roomDeletedPresenter;
    private final RoomGateway roomGateway;

    public DeleteRoomInteractor(final RoomDeletedPresenter roomDeletedPresenter, final RoomGateway roomGateway) {
        this.roomDeletedPresenter = roomDeletedPresenter;
        this.roomGateway = roomGateway;
    }

    @Override
    public void execute(final Integer roomId) {
        try {
            roomGateway.delete(roomId);
        } catch (Exception e) {
            roomDeletedPresenter.prepareFailView("Ocorreu um erro interno ao excluir a sala.");
        }
    }
}
