package com.fcs.payment.usecase.gateway;

import com.fcs.payment.entity.PaymentData;

public interface EncryptPaymentRequestDataGateway {

    String encrypt(PaymentData paymentData);
}
