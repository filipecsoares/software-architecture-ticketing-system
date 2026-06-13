package com.fcs.orders.usecase.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record OrderDetailResponseModel(Integer orderId,
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
