package com.fcs.customers.usecase.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record OrderDetailResponseModel(Integer orderId,
                                       String eventName,
                                       String sessionName,
                                       String roomName,
                                       Map<String, List<String>> ticketsByChairs,
                                       Double totalPrice,
                                       Long cardNumber,
                                       String cardHolderName,
                                       LocalDate exp,
                                       String cardBanner,
                                       String status) {
}
