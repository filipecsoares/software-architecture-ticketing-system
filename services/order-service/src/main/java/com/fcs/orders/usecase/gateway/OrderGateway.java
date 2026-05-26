package com.fcs.orders.usecase.gateway;

import com.fcs.orders.entity.Order;
import com.fcs.orders.usecase.model.CreatedOrderReservationResponseModel;
import com.fcs.orders.usecase.model.UnavailableChairsResponseModel;

public interface OrderGateway {

    UnavailableChairsResponseModel getUnavailableChairs(final Integer sessionId);

    CreatedOrderReservationResponseModel createReservation(final Order createdReservedOrder);

    boolean hasOrderInProgress(final Integer customerId);

    Integer getTotalUnavailableTicketsForTicketId(final Integer sessionId, final Integer ticketId);
}
