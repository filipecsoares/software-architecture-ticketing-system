package com.fcs.customers.adapter.entrypoint.dto;

import java.util.List;
import java.util.Map;

public record CreateOrderReservationDto(Integer eventId, Integer sessionId, Integer roomId, Map<Integer, List<String>> ticketsByChairs, Integer customerId, Double totalPrice) {

}
