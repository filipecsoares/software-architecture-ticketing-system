package com.fcs.rooms.usecase.input;

import com.fcs.rooms.usecase.model.RoomResponseModel;

import java.util.List;

public interface GetAllRoomsInputBoundary {

    List<RoomResponseModel> execute();
}
