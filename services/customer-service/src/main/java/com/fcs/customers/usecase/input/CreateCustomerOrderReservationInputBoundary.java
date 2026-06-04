package com.fcs.customers.usecase.input;

import com.fcs.customers.usecase.model.CreateCustomerOrderReservationRequestModel;

public interface CreateCustomerOrderReservationInputBoundary {
    Integer execute(CreateCustomerOrderReservationRequestModel createOrderReservationRequestModel);
}
