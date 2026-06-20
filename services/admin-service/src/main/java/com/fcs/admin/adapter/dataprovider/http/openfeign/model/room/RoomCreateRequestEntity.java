package com.fcs.admin.adapter.dataprovider.http.openfeign.model.room;

import com.fcs.admin.usecase.model.SeatModel;

import java.util.List;

public record RoomCreateRequestEntity(String name, List<SeatModel> seats) {
}
