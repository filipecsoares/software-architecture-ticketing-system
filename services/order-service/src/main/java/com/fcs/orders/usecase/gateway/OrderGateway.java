package com.fcs.orders.usecase.gateway;

import com.fcs.orders.entity.Order;
import com.fcs.orders.entity.vo.OrderStatus;
import com.fcs.orders.usecase.model.*;

import java.util.List;

public interface OrderGateway {

    UnavailableChairsResponseModel getUnavailableChairs(final Integer sessionId);

    CreateOrderReservationResponseModel createReservation(final Order createdReservedOrder);

    boolean hasOrderInProgress(final Integer customerId);

    Integer getTotalUnavailableTicketsForTicketId(final Integer sessionId, final Integer ticketId);

    OrderReservationDetailsResponseModel getOrderReservationDetails(Integer reservationId);

    CreateOrderResponseModel createOrder(Order toCreate);

    void update(Integer orderId, OrderStatus status);

    void deleteReservation(Integer reservationId);

    List<OrderDetailResponseModel> getCustomerOrdersDetails(Integer customerId);
}
