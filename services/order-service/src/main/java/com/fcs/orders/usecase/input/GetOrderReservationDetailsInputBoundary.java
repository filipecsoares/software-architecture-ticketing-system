package com.fcs.orders.usecase.input;

import com.fcs.orders.usecase.model.OrderReservationDetailsResponseModel;

public interface GetOrderReservationDetailsInputBoundary {

    OrderReservationDetailsResponseModel execute(Integer orderId);
}
