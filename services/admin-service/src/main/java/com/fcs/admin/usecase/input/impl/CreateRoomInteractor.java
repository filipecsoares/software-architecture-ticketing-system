package com.fcs.admin.usecase.input.impl;

import com.fcs.admin.usecase.gateway.RoomGateway;
import com.fcs.admin.usecase.input.CreateRoomInputBoundary;
import com.fcs.admin.usecase.model.RoomCreateModel;
import com.fcs.admin.usecase.model.RoomCreatedResponseModel;
import com.fcs.admin.usecase.presenter.RoomCreatedPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateRoomInteractor implements CreateRoomInputBoundary {

    private final RoomGateway roomGateway;
    private final RoomCreatedPresenter roomCreatedPresenter;

    @Override
    public RoomCreatedResponseModel execute(RoomCreateModel toCreate) {
        try {
            return roomCreatedPresenter.prepareSuccessView(roomGateway.create(toCreate));
        } catch (Exception e) {
            roomCreatedPresenter.prepareFailView("Ocorreu um erro ao cadastrar a sala. Causa: " + e.getMessage());
        }
        return null;
    }
}
