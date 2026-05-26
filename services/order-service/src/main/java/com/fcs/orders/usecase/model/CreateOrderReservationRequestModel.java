package com.fcs.orders.usecase.model;

import java.util.List;
import java.util.Map;

public record CreateOrderReservationRequestModel (Integer eventId, Integer sessionId, Integer roomId, Map<Integer, List<String>> ticketsByChairs, Integer customerId, Double totalPrice) {

}
