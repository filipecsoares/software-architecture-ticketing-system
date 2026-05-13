package com.fcs.rooms.adapter.entrypoint.dto;

import java.util.List;

public record CreateRoomDto(String name, List<SeatDto> seats) {
}
