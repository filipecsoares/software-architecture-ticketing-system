package com.fcs.rooms.usecase.gateway;

import com.fcs.rooms.entity.Room;
import com.fcs.rooms.usecase.model.RoomResponseModel;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomGateway {

    Integer create(final Room toCreate);

    void delete(final Integer roomId);

    List<RoomResponseModel> getAll();

    RoomResponseModel getById(final Integer roomId);

    boolean exists(final String name);

    void allocate(final Integer roomId, final LocalDateTime startDateTime, final LocalDateTime endDateTime);
}
