package com.fcs.payment.adapter.dataprovider;

import com.fcs.payment.adapter.dataprovider.http.openfeign.ExternalPaymentGatewayFeignClient;
import com.fcs.payment.adapter.dataprovider.http.openfeign.model.payment.PaymentRequestEntity;
import com.fcs.payment.adapter.dataprovider.http.openfeign.model.payment.PaymentResponseEntity;
import com.fcs.payment.usecase.gateway.PaymentProcessGateway;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentProcessorBySomeExternalPaymentGatewayDataProvider implements PaymentProcessGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProcessorBySomeExternalPaymentGatewayDataProvider.class);

    private final ExternalPaymentGatewayFeignClient externalPaymentGatewayFeignClient;

    @Override
    public Boolean process(Double totalPrice, String cardNumber, String cvv, String cardHolderName, String exp, String banner) {
        try {
            PaymentRequestEntity requestEntity = new PaymentRequestEntity(cardNumber,
                    cvv,
                    cardHolderName,
                    exp,
                    banner,
                    totalPrice);
            PaymentResponseEntity responseEntity = externalPaymentGatewayFeignClient.processPayment(requestEntity);
            if ("success".equals(responseEntity.status()))
                return true;
        } catch (FeignException.UnprocessableEntity e) {
            LOGGER.error("O pagamento nao foi autorizado pelo gateway externo", e);
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Failed to process payment in external gateway", e);
        }
        return false;
    }
}
