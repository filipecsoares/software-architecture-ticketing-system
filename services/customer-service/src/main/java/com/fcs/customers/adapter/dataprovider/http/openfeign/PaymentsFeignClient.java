package com.fcs.customers.adapter.dataprovider.http.openfeign;

import com.fcs.customers.adapter.dataprovider.http.openfeign.model.payments.CreateOrderReservationPaymentRequestEntity;
import com.fcs.customers.adapter.dataprovider.http.openfeign.model.payments.CreatedOrderReservationPaymentRequestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payments-client", url = "${spring.cloud.openfeign.client.config.payment-service.api.baseurl}")
public interface PaymentsFeignClient {

    @PostMapping("/reservation-payment")
    CreatedOrderReservationPaymentRequestResponse createReservationPaymentRequest(CreateOrderReservationPaymentRequestEntity requestEntity);
}
