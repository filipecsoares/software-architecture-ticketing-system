package com.fcs.orders.adapter.dataprovider;

import com.fcs.orders.adapter.dataprovider.jpa.entity.JpaOrderItem;
import com.fcs.orders.adapter.dataprovider.jpa.entity.JpaReservedOrder;
import com.fcs.orders.adapter.dataprovider.jpa.repository.JpaOrderItemRepository;
import com.fcs.orders.adapter.dataprovider.jpa.repository.JpaReservedOrderRepository;
import com.fcs.orders.entity.Order;
import com.fcs.orders.usecase.gateway.OrderGateway;
import com.fcs.orders.usecase.model.CreatedOrderReservationResponseModel;
import com.fcs.orders.usecase.model.UnavailableChairsResponseModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Component
public record OrderDataProviderAdapter(JpaReservedOrderRepository jpaReservedOrderRepository, JpaOrderItemRepository jpaOrderItemRepository) implements OrderGateway {

    @Override
    public UnavailableChairsResponseModel getUnavailableChairs(Integer sessionId) {
        LocalDateTime timeLimit = LocalDateTime.now().minusMinutes(5);
        Optional<List<JpaReservedOrder>> reservedOrders = jpaReservedOrderRepository.findBySessionIdAndCreationDateTimeAfter(sessionId, timeLimit);
        Set<String> unavailableChairs = new HashSet<>();
        if (reservedOrders.isPresent() && !reservedOrders.get().isEmpty()) {
            reservedOrders.get()
                    .forEach(reservedOrder -> reservedOrder.getTicketsByChairs()
                            .forEach((key, value) -> unavailableChairs.addAll(value)));
        }
        Optional<List<JpaOrderItem>> sessionOrders = jpaOrderItemRepository.findBySessionId(sessionId);
        if (sessionOrders.isPresent() && !sessionOrders.get().isEmpty()) {
            sessionOrders.get()
                    .forEach(sessionOrder -> sessionOrder.getTicketsByChairs()
                            .forEach((key, value) -> unavailableChairs.add(value)));
        }
        return new UnavailableChairsResponseModel(unavailableChairs);
    }

    @Override
    public CreatedOrderReservationResponseModel createReservation(Order createdReservedOrder) {
        JpaReservedOrder jpaReservedOrder = new JpaReservedOrder();
        jpaReservedOrder.setEventId(createdReservedOrder.getItem().eventId());
        jpaReservedOrder.setSessionId(createdReservedOrder.getItem().sessionId());
        jpaReservedOrder.setRoomId(createdReservedOrder.getItem().roomId());
        jpaReservedOrder.setTicketsByChairs(createdReservedOrder.getItem().ticketsByChairs());
        jpaReservedOrder.setCustomerId(createdReservedOrder.getCustomerId());
        jpaReservedOrder.setTotalPrice(createdReservedOrder.getTotalPrice());
        jpaReservedOrder.setCreationDateTime(LocalDateTime.now());

        Integer reservedOrderId = jpaReservedOrderRepository.save(jpaReservedOrder).getId();

        return new CreatedOrderReservationResponseModel(reservedOrderId, createdReservedOrder.getStatus());
    }

    @Override
    public boolean hasOrderInProgress(Integer customerId) {
        LocalDateTime timeLimit = LocalDateTime.now().minusMinutes(5);
        return jpaReservedOrderRepository.findByCustomerIdAndCreationDateTimeAfter(customerId, timeLimit).isPresent();
    }

    @Override
    public Integer getTotalUnavailableTicketsForTicketId(Integer sessionId, Integer ticketId) {
        LocalDateTime timeLimit = LocalDateTime.now().minusMinutes(5);
        Optional<List<JpaReservedOrder>> reservedOrders = jpaReservedOrderRepository.findBySessionIdAndTicketIdAndCreationDateTimeAfter(sessionId, ticketId, timeLimit);
        AtomicReference<Integer> unavailableTickets = new AtomicReference<>(0);
        if (reservedOrders.isPresent() && !reservedOrders.get().isEmpty()) {
            reservedOrders.get()
                    .forEach(reservedOrder -> reservedOrder.getTicketsByChairs()
                            .forEach((ticketByChairsId, chairsSize) -> {
                                if (ticketByChairsId.equals(ticketId)) {
                                    unavailableTickets.getAndSet(unavailableTickets.get() + chairsSize.size());
                                }
                            }));
        }
        Optional<List<JpaOrderItem>> sessionOrders = jpaOrderItemRepository.findBySessionIdAndTicketId(sessionId, ticketId);
        if (sessionOrders.isPresent() && !sessionOrders.get().isEmpty()) {
            sessionOrders.get()
                    .forEach(sessionOrder -> sessionOrder.getTicketsByChairs()
                            .forEach((key, value) -> {
                                if (key.equals(ticketId)) {
                                    unavailableTickets.getAndSet(unavailableTickets.get() + 1);
                                }
                            }));
        }
        return unavailableTickets.get();
    }
}
