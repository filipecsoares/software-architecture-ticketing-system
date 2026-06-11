package com.fcs.payment.usecase.input;

import com.fcs.payment.entity.PaymentData;
import com.fcs.payment.usecase.model.PaymentRequestedResponseModel;

public interface CreatePaymentRequestInputBoundary {

    PaymentRequestedResponseModel execute(Integer reservationId, PaymentData paymentData);
}
