package com.fcs.customers.adapter.dataprovider.http.openfeign;

import com.fcs.customers.adapter.dataprovider.http.openfeign.model.orders.CreateOrderReservationRequestEntity;
import com.fcs.customers.adapter.dataprovider.http.openfeign.model.orders.CreatedOrderReservationResponseEntity;
import com.fcs.customers.adapter.dataprovider.http.openfeign.model.orders.OrderDetailResponseEntity;
import com.fcs.customers.adapter.dataprovider.http.openfeign.model.orders.UnavailableChairsResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "orders-client", url = "${spring.cloud.openfeign.client.config.order-service.api.baseurl}")
public interface OrdersFeignClient {

    @GetMapping("/sessions/{sessionId}/unavailable/chairs")
    UnavailableChairsResponseEntity getUnavailableChairsToSession(@PathVariable("sessionId") Integer sessionId);

    @GetMapping("/{customerId}/in-progress")
    boolean hasOrderInProgress(@PathVariable("customerId") String customerId);

    @GetMapping("/sessions/{sessionId}/unavailable/tickets/{ticketId}")
    Integer getUnavailableTicketsToSession(@PathVariable("sessionId") Integer sessionId, @PathVariable("ticketId") Integer ticketId);

    @PostMapping("/reservation")
    CreatedOrderReservationResponseEntity createReservation(CreateOrderReservationRequestEntity createOrderReservationRequestEntity);

    @GetMapping("/orders")
    List<OrderDetailResponseEntity> getAllCustomerOrders(@RequestParam("customerId") String customerId);
}
