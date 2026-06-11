package com.fcs.payment.usecase.input.impl;

import com.fcs.payment.entity.PaymentData;
import com.fcs.payment.usecase.gateway.EncryptPaymentRequestDataGateway;
import com.fcs.payment.usecase.gateway.PaymentRequestGateway;
import com.fcs.payment.usecase.input.CreatePaymentRequestInputBoundary;
import com.fcs.payment.usecase.model.PaymentRequestedResponseModel;
import com.fcs.payment.usecase.presenter.PaymentRequestPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePaymentRequestInteractor implements CreatePaymentRequestInputBoundary {

    private final EncryptPaymentRequestDataGateway encryptPaymentRequestDataGateway;
    private final PaymentRequestGateway paymentRequestGateway;
    private final PaymentRequestPresenter paymentRequestPresenter;

    @Override
    public PaymentRequestedResponseModel execute(Integer reservationId, PaymentData paymentData) {
        if (!paymentData.isValid())
            paymentRequestPresenter.prepareFailView("Verifique que todas as informações de pagamento estão preenchidas.");
        String encryptedPaymentData = encryptPaymentRequestDataGateway.encrypt(paymentData);
        boolean result = paymentRequestGateway.request(reservationId, encryptedPaymentData);
        if (!result)
            paymentRequestPresenter.prepareFailView("Ocorreu um erro ao requisiar o pagamento para a reserva de ID " + reservationId);
        return paymentRequestPresenter.prepareSuccessView(new PaymentRequestedResponseModel(result));
    }
}
