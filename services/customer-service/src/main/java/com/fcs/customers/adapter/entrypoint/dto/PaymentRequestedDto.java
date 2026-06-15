package com.fcs.customers.adapter.entrypoint.dto;

import java.time.LocalDate;

public record PaymentRequestedDto(Integer reservationId, Long cardNumber, Integer cvv, String cardHolderName, LocalDate exp, String banner) {
}
