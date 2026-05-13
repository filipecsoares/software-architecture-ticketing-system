package com.fcs.rooms.usecase.input;

import java.time.LocalDateTime;

public interface AllocateRoomInputBoundary {

    void execute(final Integer roomId, final LocalDateTime startDateTime, final LocalDateTime endDateTime);
}
