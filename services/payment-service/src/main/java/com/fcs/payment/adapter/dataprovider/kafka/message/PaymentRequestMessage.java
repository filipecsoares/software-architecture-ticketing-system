package com.fcs.payment.adapter.dataprovider.kafka.message;

public record PaymentRequestMessage(Integer reservationId, String encryptedPaymentData) {
}
