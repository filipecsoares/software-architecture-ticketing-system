package com.fcs.rooms.usecase.input.impl;

import com.fcs.rooms.entity.Room;
import com.fcs.rooms.usecase.gateway.RoomGateway;
import com.fcs.rooms.usecase.input.CreateRoomInputBoundary;
import com.fcs.rooms.usecase.model.RoomCreatedResponseModel;
import com.fcs.rooms.usecase.presenter.RoomCreatedPresenter;
import org.springframework.stereotype.Service;

@Service
public class CreateRoomInteractor implements CreateRoomInputBoundary {

    private final RoomGateway roomGateway;
    private final RoomCreatedPresenter roomCreatedPresenter;

    public CreateRoomInteractor(final RoomGateway roomGateway, final RoomCreatedPresenter roomCreatedPresenter) {
        this.roomGateway = roomGateway;
        this.roomCreatedPresenter = roomCreatedPresenter;
    }

    @Override
    public RoomCreatedResponseModel execute(final Room toCreate) {
        if (!toCreate.isValid())
            roomCreatedPresenter.prepareFailView("Verifique que todas as informações da sala estão preenchidas.");
        if (toCreate.getAllocations() != null && !toCreate.getAllocations().isEmpty())
            roomCreatedPresenter.prepareFailView("A sala não deve ter alocações no momento da criação.");
        boolean roomAlreadyExists = roomGateway.exists(toCreate.getName());
        if (roomAlreadyExists)
            roomCreatedPresenter.prepareFailView("Já existe uma sala cadastrada com o nome '" + toCreate.getName() + "'");
        Integer createdId = roomGateway.create(toCreate);
        toCreate.setId(createdId);
        return roomCreatedPresenter.prepareSuccessView(toCreate);
    }
}
