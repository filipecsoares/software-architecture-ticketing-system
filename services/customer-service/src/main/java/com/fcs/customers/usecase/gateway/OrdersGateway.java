package com.fcs.customers.usecase.gateway;

import java.util.List;
import java.util.Map;

public interface OrdersGateway {
    List<String> getUnavailableChairsToSession(Integer sessionId);

    Integer getUnavailableTicketsToSession(Integer sessionId, Integer ticketId);

    boolean hasOrderInProgress(Integer customerId);

    Integer createReservation(Integer eventId, Integer sessionId, Integer roomID, Map<Integer, List<String>> ticketsByChairs, Integer customerId, Double totalPrice);
}
