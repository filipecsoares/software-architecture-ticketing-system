package com.fcs.customers.usecase.gateway;

import com.fcs.customers.usecase.model.PaymentRequestedResponseModel;

import java.time.LocalDate;

public interface PaymentGateway {
    PaymentRequestedResponseModel requestReservationPayment(Integer reservationId,
                                                            Long cardNumber,
                                                            Integer cvv,
                                                            String cardHolderName,
                                                            LocalDate exp,
                                                            String banner);
}
