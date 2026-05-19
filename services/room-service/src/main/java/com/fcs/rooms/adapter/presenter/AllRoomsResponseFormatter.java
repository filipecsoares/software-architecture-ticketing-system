package com.fcs.rooms.adapter.presenter;

import com.fcs.rooms.usecase.exception.BusinessException;
import com.fcs.rooms.usecase.model.RoomResponseModel;
import com.fcs.rooms.usecase.presenter.AllRoomsPresenter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllRoomsResponseFormatter implements AllRoomsPresenter {

    @Override
    public List<RoomResponseModel> prepareSuccessView(final List<RoomResponseModel> allRooms) {
        return allRooms;
    }

    @Override
    public void prepareFailView(final String error) {
        throw new BusinessException(error);
    }
}
