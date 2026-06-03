package com.fcs.customers.usecase.gateway;

import com.fcs.customers.usecase.model.RoomDetailsWithChairsAvailabilityToCustomerResponseModel;

public interface RoomsGateway {
    RoomDetailsWithChairsAvailabilityToCustomerResponseModel getRoomById(Integer roomId);
}
