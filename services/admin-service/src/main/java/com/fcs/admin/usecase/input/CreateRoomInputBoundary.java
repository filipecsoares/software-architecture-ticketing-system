package com.fcs.admin.usecase.input;

import com.fcs.admin.usecase.model.RoomCreateModel;
import com.fcs.admin.usecase.model.RoomCreatedResponseModel;

public interface CreateRoomInputBoundary {

    RoomCreatedResponseModel execute(RoomCreateModel toCreate);
}
