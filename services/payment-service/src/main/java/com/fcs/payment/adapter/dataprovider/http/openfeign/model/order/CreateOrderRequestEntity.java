package com.fcs.payment.adapter.dataprovider.http.openfeign.model.order;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record CreateOrderRequestEntity (Integer customerId, OrderItemRequestEntity item, Double totalPrice, OrderPaymentRequestEntity payment) {

    public record OrderItemRequestEntity(Integer eventId, Integer sessionId, Integer roomId, Map<Integer, List<String>> ticketsByChairs) {

    }

    public record OrderPaymentRequestEntity(Long cardNumber, String cardHolderName, LocalDate exp, String banner) {

    }
}
