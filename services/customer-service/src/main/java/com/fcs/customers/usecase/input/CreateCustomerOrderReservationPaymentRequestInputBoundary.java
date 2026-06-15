package com.fcs.customers.usecase.input;

import com.fcs.customers.usecase.model.PaymentRequestedResponseModel;

import java.time.LocalDate;

public interface CreateCustomerOrderReservationPaymentRequestInputBoundary {

    PaymentRequestedResponseModel execute(Integer reservationId,
                                          Long cardNumber,
                                          Integer cvv,
                                          String cardHolderName,
                                          LocalDate exp,
                                          String banner);
}
