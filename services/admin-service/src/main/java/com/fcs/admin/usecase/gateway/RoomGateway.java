package com.fcs.admin.usecase.gateway;

import com.fcs.admin.usecase.model.RoomCreateModel;
import com.fcs.admin.usecase.model.RoomCreatedResponseModel;
import com.fcs.admin.usecase.model.RoomResponseModel;

import java.util.List;

public interface RoomGateway {

    RoomCreatedResponseModel create(RoomCreateModel toCreate);

    void delete(Integer roomId);

    List<RoomResponseModel> getAll();
}
