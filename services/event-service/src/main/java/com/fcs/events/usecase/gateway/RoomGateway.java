package com.fcs.events.usecase.gateway;

import com.fcs.events.usecase.model.RoomModel;

import java.time.LocalDateTime;

public interface RoomGateway {

    RoomModel findBy(final Integer id, final LocalDateTime requestUseDateTime);
    boolean allocate(final Integer roomId, final LocalDateTime startDateTime, final LocalDateTime endDateTime);
}
