package com.fcs.payment.usecase.gateway;

public interface PaymentProcessGateway {

    Boolean process(Double totalPrice, String cardNumber, String cvv, String cardHolderName, String exp, String banner);
}
