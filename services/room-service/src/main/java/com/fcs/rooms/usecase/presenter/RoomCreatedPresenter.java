package com.fcs.rooms.usecase.presenter;

import com.fcs.rooms.entity.Room;
import com.fcs.rooms.usecase.model.RoomCreatedResponseModel;

public interface RoomCreatedPresenter {

    RoomCreatedResponseModel prepareSuccessView(final Room room);

    void prepareFailView(final String error);
}
