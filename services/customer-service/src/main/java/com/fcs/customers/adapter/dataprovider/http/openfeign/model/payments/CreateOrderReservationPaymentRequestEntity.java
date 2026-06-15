package com.fcs.customers.adapter.dataprovider.http.openfeign.model.payments;

import java.time.LocalDate;

public record CreateOrderReservationPaymentRequestEntity(Integer reservationId, Long cardNumber, Integer cvv, String cardHolderName, LocalDate exp, String banner) {
}
