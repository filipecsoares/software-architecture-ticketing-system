package com.fcs.orders.adapter.dataprovider;

import com.fcs.orders.adapter.dataprovider.jpa.entity.JpaOrder;
import com.fcs.orders.adapter.dataprovider.jpa.entity.JpaOrderItem;
import com.fcs.orders.adapter.dataprovider.jpa.entity.JpaReservedOrder;
import com.fcs.orders.adapter.dataprovider.jpa.repository.JpaOrderItemRepository;
import com.fcs.orders.adapter.dataprovider.jpa.repository.JpaOrderRepository;
import com.fcs.orders.adapter.dataprovider.jpa.repository.JpaReservedOrderRepository;
import com.fcs.orders.entity.Order;
import com.fcs.orders.entity.vo.OrderStatus;
import com.fcs.orders.usecase.exception.BusinessException;
import com.fcs.orders.usecase.gateway.OrderGateway;
import com.fcs.orders.usecase.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
public record OrderDataProviderAdapter(JpaReservedOrderRepository jpaReservedOrderRepository, JpaOrderItemRepository jpaOrderItemRepository, JpaOrderRepository jpaOrderRepository) implements OrderGateway {

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
                    .forEach(sessionOrder -> {
                        if (!sessionOrder.getOrder().getStatus().equals("DENIED")) {
                            sessionOrder.getTicketsByChairs()
                                    .forEach((key, value) -> unavailableChairs.addAll(value));
                        }
                    });
        }
        return new UnavailableChairsResponseModel(unavailableChairs);
    }

    @Override
    public CreateOrderReservationResponseModel createReservation(Order createdReservedOrder) {
        JpaReservedOrder jpaReservedOrder = new JpaReservedOrder();
        jpaReservedOrder.setEventId(createdReservedOrder.getItem().eventId());
        jpaReservedOrder.setSessionId(createdReservedOrder.getItem().sessionId());
        jpaReservedOrder.setRoomId(createdReservedOrder.getItem().roomId());
        jpaReservedOrder.setTicketsByChairs(createdReservedOrder.getItem().ticketsByChairs());
        jpaReservedOrder.setCustomerId(createdReservedOrder.getCustomerId());
        jpaReservedOrder.setTotalPrice(createdReservedOrder.getTotalPrice());
        jpaReservedOrder.setCreationDateTime(LocalDateTime.now());

        Integer reservedOrderId = jpaReservedOrderRepository.save(jpaReservedOrder).getId();

        return new CreateOrderReservationResponseModel(reservedOrderId, createdReservedOrder.getStatus());
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
                                    if (!sessionOrder.getOrder().getStatus().equals("DENIED")) {
                                        unavailableTickets.getAndSet(unavailableTickets.get() + value.size());
                                    }
                                }
                            }));
        }
        return unavailableTickets.get();
    }

    @Override
    public OrderReservationDetailsResponseModel getOrderReservationDetails(Integer reservationId) {
        LocalDateTime timeLimit = LocalDateTime.now().minusMinutes(5);
        return jpaReservedOrderRepository.findByIdAndCreationDateTimeAfter(reservationId, timeLimit)
                .map(jpaReservedOrder -> new OrderReservationDetailsResponseModel(
                        jpaReservedOrder.getId(),
                        jpaReservedOrder.getEventId(),
                        jpaReservedOrder.getSessionId(),
                        jpaReservedOrder.getRoomId(),
                        jpaReservedOrder.getTicketsByChairs(),
                        jpaReservedOrder.getCustomerId(),
                        jpaReservedOrder.getTotalPrice()))
                .orElseThrow(() -> new BusinessException("Reserva com id " + reservationId + " nao encontrada ou expirada (com mais de 5 minutos)."));
    }

    @Override
    public CreateOrderResponseModel createOrder(Order toCreate) {

        JpaOrder jpaOrder = new JpaOrder();
        jpaOrder.setCustomerId(toCreate.getCustomerId());
        jpaOrder.setTotalPrice(toCreate.getTotalPrice());
        jpaOrder.setCardNumber(toCreate.getPayment().cardNumber());
        jpaOrder.setCardHolderName(toCreate.getPayment().cardHolderName());
        jpaOrder.setExp(toCreate.getPayment().exp());
        jpaOrder.setCardBanner(toCreate.getPayment().banner().name());
        jpaOrder.setStatus(toCreate.getStatus().toString());

        Integer orderId = jpaOrderRepository.save(jpaOrder).getId();

        JpaOrderItem jpaOrderItem = new JpaOrderItem();
        jpaOrderItem.setEventId(toCreate.getItem().eventId());
        jpaOrderItem.setSessionId(toCreate.getItem().sessionId());
        jpaOrderItem.setRoomId(toCreate.getItem().roomId());
        jpaOrderItem.setTicketsByChairs(toCreate.getItem().ticketsByChairs());
        jpaOrderItem.setOrder(jpaOrder);

        jpaOrderItemRepository.save(jpaOrderItem);

        return new CreateOrderResponseModel(orderId, toCreate.getStatus());
    }

    @Override
    public void update(Integer orderId, OrderStatus status) {
        JpaOrder jpaOrder = jpaOrderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException("Nao foi possivel encontrar o pedido com ID " + orderId));
        jpaOrder.setStatus(status.toString());
        jpaOrderRepository.save(jpaOrder);
    }

    @Override
    public void deleteReservation(Integer reservationId) {
        jpaReservedOrderRepository.deleteById(reservationId);
    }

    @Override
    public List<OrderDetailResponseModel> getCustomerOrdersDetails(Integer customerId) {
        List<OrderDetailResponseModel> orderDetailResponseModels = new ArrayList<>();
        List<JpaOrder> jpaOrders = jpaOrderRepository.findByCustomerId(customerId);
        if (jpaOrders != null && !jpaOrders.isEmpty()) {
            List<JpaOrderItem> jpaOrderItems = new ArrayList<>();
            jpaOrders.forEach(jpaOrder -> jpaOrderItems.addAll(jpaOrderItemRepository.findByOrderId(jpaOrder.getId())));
            jpaOrders.forEach(jpaOrder -> {
                List<JpaOrderItem> filteredJpaOrderItems = jpaOrderItems.stream().filter(jpaOrderItem -> jpaOrderItem.getOrder().equals(jpaOrder)).toList();
                filteredJpaOrderItems.forEach(filteredJpaOrderItem -> orderDetailResponseModels.add(new OrderDetailResponseModel(filteredJpaOrderItem.getOrder().getId(),
                        filteredJpaOrderItem.getEventId(),
                        filteredJpaOrderItem.getSessionId(),
                        filteredJpaOrderItem.getRoomId(),
                        filteredJpaOrderItem.getTicketsByChairs(),
                        jpaOrder.getTotalPrice(),
                        jpaOrder.getCardNumber(),
                        jpaOrder.getCardHolderName(),
                        jpaOrder.getExp(),
                        jpaOrder.getCardBanner(),
                        jpaOrder.getStatus())));
            });
        }
        return orderDetailResponseModels;
    }
}
