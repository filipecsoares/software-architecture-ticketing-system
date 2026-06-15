package com.fcs.customers.adapter.dataprovider.http.openfeign.model.orders;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record OrderDetailResponseEntity (Integer orderId,
                                         Integer eventId,
                                         Integer sessionId,
                                         Integer roomId,
                                         Map<Integer, List<String>> ticketsByChairs,
                                         Double totalPrice,
                                         Long cardNumber,
                                         String cardHolderName,
                                         LocalDate exp,
                                         String cardBanner,
                                         String status) {
}
