package com.fcs.customers.adapter.dataprovider;

import com.fcs.customers.adapter.dataprovider.http.openfeign.PaymentsFeignClient;
import com.fcs.customers.adapter.dataprovider.http.openfeign.model.payments.CreateOrderReservationPaymentRequestEntity;
import com.fcs.customers.adapter.dataprovider.http.openfeign.model.payments.CreatedOrderReservationPaymentRequestResponse;
import com.fcs.customers.usecase.gateway.PaymentGateway;
import com.fcs.customers.usecase.model.PaymentRequestedResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PaymentDataProviderByExternalApiAdapter implements PaymentGateway {

    private final PaymentsFeignClient paymentsFeignClient;

    @Override
    public PaymentRequestedResponseModel requestReservationPayment(Integer reservationId,
                                                                   Long cardNumber,
                                                                   Integer cvv,
                                                                   String cardHolderName,
                                                                   LocalDate exp,
                                                                   String banner) {
        CreatedOrderReservationPaymentRequestResponse responseEntity = paymentsFeignClient.createReservationPaymentRequest(new CreateOrderReservationPaymentRequestEntity(
                reservationId,
                cardNumber,
                cvv,
                cardHolderName,
                exp,
                banner
        ));
        return new PaymentRequestedResponseModel(responseEntity.requestedWithSuccess());
    }
}
