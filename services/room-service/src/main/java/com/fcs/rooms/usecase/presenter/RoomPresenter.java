package com.fcs.rooms.usecase.presenter;

import com.fcs.rooms.usecase.model.RoomResponseModel;

public interface RoomPresenter {

    RoomResponseModel prepareSuccessView(final RoomResponseModel roomResponseModel);

    void prepareFailView(final String error);
}
