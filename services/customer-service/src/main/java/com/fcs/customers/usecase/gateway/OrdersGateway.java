package com.fcs.customers.usecase.gateway;

import com.fcs.customers.usecase.model.OrderResponseModel;

import java.util.List;
import java.util.Map;

public interface OrdersGateway {
    List<String> getUnavailableChairsToSession(Integer sessionId);

    Integer getUnavailableTicketsToSession(Integer sessionId, Integer ticketId);

    boolean hasOrderInProgress(String customerId);

    Integer createReservation(Integer eventId, Integer sessionId, Integer roomID, Map<Integer, List<String>> ticketsByChairs, String customerId, Double totalPrice);

    List<OrderResponseModel> getAllByCustomerId(String customerId);
}
