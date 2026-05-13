package com.fcs.rooms.adapter.entrypoint.dto;

import java.time.LocalDateTime;

public record AllocateRoomDto(LocalDateTime startDateTime, LocalDateTime endDateTime) {
}
