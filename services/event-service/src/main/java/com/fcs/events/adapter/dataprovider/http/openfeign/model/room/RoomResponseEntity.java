package com.fcs.events.adapter.dataprovider.http.openfeign.model.room;

import java.util.List;

public class RoomResponseEntity {
    private Integer id;
    private String name;
    private List<SeatResponseEntity> seats;
    private List<AllocationResponseEntity> allocations;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SeatResponseEntity> getSeats() {
        return seats;
    }

    public List<AllocationResponseEntity> getAllocations() {
        return allocations;
    }
}
