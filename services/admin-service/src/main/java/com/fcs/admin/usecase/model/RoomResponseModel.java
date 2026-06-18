package com.fcs.admin.usecase.model;

import java.util.List;

public record RoomResponseModel(Integer id, String name, List<SeatResponseModel> seats, List<AllocationResponseModel> allocations) {
}
