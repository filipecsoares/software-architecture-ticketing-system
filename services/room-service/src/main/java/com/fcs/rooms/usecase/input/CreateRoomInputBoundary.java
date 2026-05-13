package com.fcs.rooms.usecase.input;

import com.fcs.rooms.entity.Room;
import com.fcs.rooms.usecase.model.RoomCreatedResponseModel;

public interface CreateRoomInputBoundary {

    RoomCreatedResponseModel execute(Room toCreate);
}
