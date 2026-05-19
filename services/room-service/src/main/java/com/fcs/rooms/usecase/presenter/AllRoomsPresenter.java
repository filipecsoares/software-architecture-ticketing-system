package com.fcs.rooms.usecase.presenter;

import com.fcs.rooms.usecase.model.RoomResponseModel;

import java.util.List;

public interface AllRoomsPresenter {

    List<RoomResponseModel> prepareSuccessView(final List<RoomResponseModel> allRooms);

    void prepareFailView(final String error);
}
