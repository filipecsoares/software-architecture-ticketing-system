package com.fcs.payment.usecase.gateway;

public interface DecryptPaymentRequestDataGateway {

    String decrypt(String encryptedPaymentRequestValue);
}
