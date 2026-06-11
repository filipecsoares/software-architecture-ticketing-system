package com.fcs.payment.adapter.dataprovider.http.openfeign.model.payment;

public record PaymentRequestEntity(String cardNumber, String cvv, String cardHolderName, String exp, String banner, Double totalPrice) {
}
