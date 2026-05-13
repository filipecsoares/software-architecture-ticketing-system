package com.fcs.rooms.usecase.model;

import java.time.LocalDateTime;

public record AllocationResponseModel(Integer id, LocalDateTime startDateTime, LocalDateTime endDateTime) {
}
