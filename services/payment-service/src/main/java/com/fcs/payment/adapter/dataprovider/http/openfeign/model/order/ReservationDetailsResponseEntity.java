package com.fcs.payment.adapter.dataprovider.http.openfeign.model.order;

import java.util.List;
import java.util.Map;

public record ReservationDetailsResponseEntity(Integer orderId,
                                               Integer eventId,
                                               Integer sessionId,
                                               Integer roomId,
                                               Map<Integer, List<String>> ticketsByChairs,
                                               Integer customerId,
                                               Double totalPrice) {

}
