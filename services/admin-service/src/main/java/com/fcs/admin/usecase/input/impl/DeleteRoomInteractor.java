package com.fcs.admin.usecase.input.impl;

import com.fcs.admin.usecase.gateway.RoomGateway;
import com.fcs.admin.usecase.input.DeleteRoomInputBoundary;
import com.fcs.admin.usecase.presenter.RoomDeletedPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteRoomInteractor implements DeleteRoomInputBoundary {

    private final RoomDeletedPresenter roomDeletedPresenter;
    private final RoomGateway roomGateway;

    @Override
    public void execute(Integer roomId) {
        try {
            roomGateway.delete(roomId);
        } catch (Exception e) {
            roomDeletedPresenter.prepareFailView("Ocorreu um erro interno ao excluir a sala. Causa: " + e.getMessage());
        }
    }
}
