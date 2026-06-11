package com.fcs.payment.adapter.dataprovider.http.openfeign;

import com.fcs.payment.adapter.dataprovider.http.openfeign.model.order.CreateOrderRequestEntity;
import com.fcs.payment.adapter.dataprovider.http.openfeign.model.order.CreateOrderResponseEntity;
import com.fcs.payment.adapter.dataprovider.http.openfeign.model.order.ReservationDetailsResponseEntity;
import com.fcs.payment.adapter.dataprovider.http.openfeign.model.order.UpdateOrderStatusRequestEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "orders-client", url = "${spring.cloud.openfeign.client.config.order-service.api.baseurl}")
public interface OrdersFeignClient {

    @GetMapping("/reservation/{reservationId}")
    ReservationDetailsResponseEntity getReservationDetails(@PathVariable("reservationId") Integer reservationId);

    @PostMapping("/order")
    CreateOrderResponseEntity createOrder(@RequestBody CreateOrderRequestEntity orderRequestEntity);

    @PutMapping("/order/{orderId}")
    void updateOrderStatus(@PathVariable("orderId") Integer orderId, @RequestBody UpdateOrderStatusRequestEntity updateOrderStatusRequestEntity);

    @DeleteMapping("/reservation/{reservationId}")
    void deleteOrderReservation(@PathVariable("reservationId") Integer reservationId);
}
