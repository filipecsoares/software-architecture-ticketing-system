package com.fcs.customers.usecase.model;

import lombok.Data;

import java.util.List;

@Data
public class RoomDetailsWithChairsAvailabilityToCustomerResponseModel {

    private Integer roomId;
    private String roomName;
    private List<SeatResponseModel> seats;
}
