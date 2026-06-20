package com.fcs.admin.adapter.dataprovider.http.openfeign.model.room;

import java.util.List;

public record RoomResponseEntity(Integer id, String name, List<SeatResponseEntity> seats, List<AllocationResponseEntity> allocations) {
}
