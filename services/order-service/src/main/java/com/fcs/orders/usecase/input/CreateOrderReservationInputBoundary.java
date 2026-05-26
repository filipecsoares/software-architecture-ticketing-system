package com.fcs.orders.usecase.input;

import com.fcs.orders.usecase.model.CreateOrderReservationRequestModel;
import com.fcs.orders.usecase.model.CreatedOrderReservationResponseModel;

public interface CreateOrderReservationInputBoundary {

    CreatedOrderReservationResponseModel execute(CreateOrderReservationRequestModel reservationRequestModel);
}
