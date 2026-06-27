package com.fcs.customers.adapter.dataprovider.http.openfeign.model.orders;

import java.util.List;
import java.util.Map;

public record CreateOrderReservationRequestEntity(Integer eventId, Integer sessionId, Integer roomId, Map<Integer, List<String>> ticketsByChairs, String customerId, Double totalPrice) {

}
