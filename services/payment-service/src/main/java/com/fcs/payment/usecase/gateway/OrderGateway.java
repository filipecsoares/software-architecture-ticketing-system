package com.fcs.payment.usecase.gateway;

import com.fcs.payment.usecase.model.CreateOrderRequestModel;
import com.fcs.payment.usecase.model.CreatedOrderResponseModel;
import com.fcs.payment.usecase.model.OrderReservationDetailsResponseModel;

public interface OrderGateway {

    OrderReservationDetailsResponseModel getReservationDetails(Integer reservationId);

    CreatedOrderResponseModel createOrder(CreateOrderRequestModel toCreate);

    void updateOrderStatus(Integer orderId, String status);

    void deleteOrderReservation(Integer reservationId);
}
