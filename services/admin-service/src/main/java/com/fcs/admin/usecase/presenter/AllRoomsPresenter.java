package com.fcs.admin.usecase.presenter;

import com.fcs.admin.usecase.model.RoomResponseModel;

import java.util.List;

public interface AllRoomsPresenter extends BasePresenter {

    List<RoomResponseModel> prepareSuccessView(List<RoomResponseModel> allRooms);
}
