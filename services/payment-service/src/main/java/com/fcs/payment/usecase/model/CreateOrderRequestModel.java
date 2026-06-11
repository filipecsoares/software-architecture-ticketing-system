package com.fcs.payment.usecase.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record CreateOrderRequestModel(Integer customerId, Integer eventId, Integer sessionId, Integer roomId,
                                      Map<Integer, List<String>> ticketsByChairs, Double totalPrice, Long cardNumber,
                                      String cardHolderName, LocalDate exp, String banner) {
}
