package com.fcs.rooms.usecase.input;

import com.fcs.rooms.usecase.model.RoomResponseModel;

public interface GetRoomInputBoundary {

    RoomResponseModel execute(final Integer roomId);
}
