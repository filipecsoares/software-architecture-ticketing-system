package com.fcs.customers.usecase.input;

import com.fcs.customers.usecase.model.RoomDetailsWithChairsAvailabilityToCustomerResponseModel;

public interface ListChairsWithAvailabilityToCustomerInputBoundary {
    RoomDetailsWithChairsAvailabilityToCustomerResponseModel execute(Integer roomId, Integer sessionId);
}
