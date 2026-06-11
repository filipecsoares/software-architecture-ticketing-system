package com.fcs.payment.usecase.input;

import com.fcs.payment.usecase.model.PaymentProcessedResponseModel;

public interface ProcessPaymentRequestInputBoundary {

    PaymentProcessedResponseModel execute(Integer reservationId, String encryptedPaymentData);
}
