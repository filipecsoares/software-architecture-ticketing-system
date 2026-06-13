package com.fcs.orders.usecase.input;

import com.fcs.orders.usecase.model.CreateOrderReservationRequestModel;
import com.fcs.orders.usecase.model.CreateOrderReservationResponseModel;

public interface CreateOrderReservationInputBoundary {

    CreateOrderReservationResponseModel execute(CreateOrderReservationRequestModel reservationRequestModel);
}
