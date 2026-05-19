package com.fcs.rooms.usecase.input.impl;

import com.fcs.rooms.usecase.gateway.RoomGateway;
import com.fcs.rooms.usecase.input.GetAllRoomsInputBoundary;
import com.fcs.rooms.usecase.model.RoomResponseModel;
import com.fcs.rooms.usecase.presenter.AllRoomsPresenter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllRoomsInteractor implements GetAllRoomsInputBoundary {

    private final AllRoomsPresenter allRoomsPresenter;
    private final RoomGateway roomGateway;

    public GetAllRoomsInteractor(final AllRoomsPresenter allRoomsPresenter, final RoomGateway roomGateway) {
        this.allRoomsPresenter = allRoomsPresenter;
        this.roomGateway = roomGateway;
    }

    @Override
    public List<RoomResponseModel> execute() {
        List<RoomResponseModel> allRooms = new ArrayList<>();
        try {
            allRooms = roomGateway.getAll();
        } catch (Exception e) {
            allRoomsPresenter.prepareFailView("Ocorreu um erro interno ao obter todas as salas cadastradas.");
        }
        return allRoomsPresenter.prepareSuccessView(allRooms);
    }
}
