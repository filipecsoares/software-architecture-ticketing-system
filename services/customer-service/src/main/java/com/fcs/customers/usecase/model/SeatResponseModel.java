package com.fcs.customers.usecase.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatResponseModel {
    private Integer id;
    private String name;
    private PositionResponseModel position;
    private boolean available;
}
