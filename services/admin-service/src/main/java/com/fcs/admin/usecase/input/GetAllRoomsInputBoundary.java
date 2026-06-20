package com.fcs.admin.usecase.input;

import com.fcs.admin.usecase.model.RoomResponseModel;

import java.util.List;

public interface GetAllRoomsInputBoundary {

    List<RoomResponseModel> execute();
}
