package com.fcs.payment.adapter.dataprovider.http.openfeign;

import com.fcs.payment.adapter.dataprovider.http.openfeign.model.payment.PaymentRequestEntity;
import com.fcs.payment.adapter.dataprovider.http.openfeign.model.payment.PaymentResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-client", url = "${spring.cloud.openfeign.client.config.payment-service.api.baseurl}")
public interface ExternalPaymentGatewayFeignClient {

    @PostMapping("/api/payment")
    PaymentResponseEntity processPayment(PaymentRequestEntity requestEntity);
}
