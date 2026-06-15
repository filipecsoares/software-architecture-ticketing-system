package com.fcs.customers.usecase.input.impl;

import com.fcs.customers.usecase.gateway.PaymentGateway;
import com.fcs.customers.usecase.input.CreateCustomerOrderReservationPaymentRequestInputBoundary;
import com.fcs.customers.usecase.model.PaymentRequestedResponseModel;
import com.fcs.customers.usecase.presenter.CustomerOrderReservationPaymentRequestPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateCustomerOrderReservationPaymentRequestInteractor implements CreateCustomerOrderReservationPaymentRequestInputBoundary {

    private final PaymentGateway paymentGateway;
    private final CustomerOrderReservationPaymentRequestPresenter presenter;

    @Override
    public PaymentRequestedResponseModel execute(Integer reservationId,
                                                 Long cardNumber,
                                                 Integer cvv,
                                                 String cardHolderName,
                                                 LocalDate exp,
                                                 String banner) {
        try {
            PaymentRequestedResponseModel responseModel = paymentGateway.requestReservationPayment(reservationId,
                    cardNumber,
                    cvv,
                    cardHolderName,
                    exp,
                    banner);
            if (!responseModel.requestedWithSuccess())
                presenter.prepareFailView("Ocorreu um erro ao requisitar o pagamento ao 'payment-service'");
            presenter.prepareSuccessView(responseModel);
        } catch (Exception e) {
            presenter.prepareFailView("Ocorreu um erro ao requisitar o pagamento da reserva de ID '" + reservationId + "'. Causa: " + e);
        }
        return null;
    }
}
