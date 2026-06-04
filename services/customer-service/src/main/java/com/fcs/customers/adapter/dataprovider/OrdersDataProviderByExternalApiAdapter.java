package com.fcs.customers.adapter.dataprovider;

import com.fcs.customers.adapter.dataprovider.http.openfeign.OrdersFeignClient;
import com.fcs.customers.adapter.dataprovider.http.openfeign.model.orders.CreateOrderReservationRequestEntity;
import com.fcs.customers.adapter.dataprovider.http.openfeign.model.orders.CreatedOrderReservationResponseEntity;
import com.fcs.customers.adapter.dataprovider.http.openfeign.model.orders.UnavailableChairsResponseEntity;
import com.fcs.customers.usecase.gateway.OrdersGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrdersDataProviderByExternalApiAdapter implements OrdersGateway {

    private final OrdersFeignClient ordersFeignClient;

    @Override
    public List<String> getUnavailableChairsToSession(Integer sessionId) {
        UnavailableChairsResponseEntity unavailableChairsResponseEntity = ordersFeignClient.getUnavailableChairsToSession(sessionId);
        if (unavailableChairsResponseEntity != null &&
                unavailableChairsResponseEntity.unavailableChairs() != null &&
                !unavailableChairsResponseEntity.unavailableChairs().isEmpty()) {
            return new ArrayList<>(unavailableChairsResponseEntity.unavailableChairs());
        }
        return List.of();
    }

    @Override
    public Integer getUnavailableTicketsToSession(Integer sessionId, Integer ticketId) {
        return ordersFeignClient.getUnavailableTicketsToSession(sessionId, ticketId);
    }

    @Override
    public boolean hasOrderInProgress(Integer customerId) {
        return ordersFeignClient.hasOrderInProgress(customerId);
    }

    @Override
    public Integer createReservation(Integer eventId, Integer sessionId, Integer roomId, Map<Integer, List<String>> ticketsByChairs, Integer customerId, Double totalPrice) {
        CreateOrderReservationRequestEntity createOrderReservationRequestEntity
                = new CreateOrderReservationRequestEntity(eventId, sessionId, roomId, ticketsByChairs, customerId, totalPrice);
        CreatedOrderReservationResponseEntity createdOrderReservationResponseEntity
                = ordersFeignClient.createReservation(createOrderReservationRequestEntity);
        return createdOrderReservationResponseEntity.orderId();
    }
}
