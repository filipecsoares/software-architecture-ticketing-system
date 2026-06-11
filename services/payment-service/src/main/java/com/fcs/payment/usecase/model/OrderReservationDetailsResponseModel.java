package com.fcs.payment.usecase.model;

import java.util.List;
import java.util.Map;

public record OrderReservationDetailsResponseModel(Integer orderId,
                                                   Integer eventId,
                                                   Integer sessionId,
                                                   Integer roomId,
                                                   Map<Integer, List<String>> ticketsByChairs,
                                                   Integer customerId,
                                                   Double totalPrice) {

}
