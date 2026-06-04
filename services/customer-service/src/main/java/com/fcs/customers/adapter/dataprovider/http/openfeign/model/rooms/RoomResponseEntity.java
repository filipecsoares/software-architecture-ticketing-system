package com.fcs.customers.adapter.dataprovider.http.openfeign.model.rooms;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RoomResponseEntity {

    private Integer id;
    private String name;
    private List<SeatResponseEntity> seats;
}
