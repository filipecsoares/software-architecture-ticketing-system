package com.fcs.admin.usecase.input.impl;

import com.fcs.admin.usecase.gateway.RoomGateway;
import com.fcs.admin.usecase.input.GetAllRoomsInputBoundary;
import com.fcs.admin.usecase.model.RoomResponseModel;
import com.fcs.admin.usecase.presenter.AllRoomsPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllRoomsInteractor implements GetAllRoomsInputBoundary {

    private final AllRoomsPresenter allRoomsPresenter;
    private final RoomGateway roomGateway;

    @Override
    public List<RoomResponseModel> execute() {
        List<RoomResponseModel> allRooms = new ArrayList<>();
        try {
            allRooms = roomGateway.getAll();
        } catch (Exception e) {
            allRoomsPresenter.prepareFailView("Ocorreu um erro interno ao obter todas as salas cadastradas. Causa: " + e.getMessage());
        }
        return allRoomsPresenter.prepareSuccessView(allRooms);
    }
}
