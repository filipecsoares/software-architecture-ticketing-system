package com.fcs.customers.adapter.dataprovider.http.openfeign.model.rooms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponseEntity {
    private Integer id;
    private String name;
    private PositionResponseEntity position;
    private boolean available;
}
