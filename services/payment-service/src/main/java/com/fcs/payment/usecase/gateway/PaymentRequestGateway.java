package com.fcs.payment.usecase.gateway;

public interface PaymentRequestGateway {

    Boolean request(Integer reservationId, String encryptedPaymentData);
}
