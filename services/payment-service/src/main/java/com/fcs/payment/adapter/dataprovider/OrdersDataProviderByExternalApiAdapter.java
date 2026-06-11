package com.fcs.payment.adapter.dataprovider;

import com.fcs.payment.adapter.dataprovider.http.openfeign.OrdersFeignClient;
import com.fcs.payment.adapter.dataprovider.http.openfeign.model.order.CreateOrderRequestEntity;
import com.fcs.payment.adapter.dataprovider.http.openfeign.model.order.CreateOrderResponseEntity;
import com.fcs.payment.adapter.dataprovider.http.openfeign.model.order.ReservationDetailsResponseEntity;
import com.fcs.payment.adapter.dataprovider.http.openfeign.model.order.UpdateOrderStatusRequestEntity;
import com.fcs.payment.usecase.gateway.OrderGateway;
import com.fcs.payment.usecase.model.CreateOrderRequestModel;
import com.fcs.payment.usecase.model.CreatedOrderResponseModel;
import com.fcs.payment.usecase.model.OrderReservationDetailsResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrdersDataProviderByExternalApiAdapter implements OrderGateway {

    private final OrdersFeignClient ordersFeignClient;

    @Override
    public OrderReservationDetailsResponseModel getReservationDetails(Integer reservationId) {
        ReservationDetailsResponseEntity detailsResponseEntity = ordersFeignClient.getReservationDetails(reservationId);
        return new OrderReservationDetailsResponseModel(
                detailsResponseEntity.orderId(),
                detailsResponseEntity.eventId(),
                detailsResponseEntity.sessionId(),
                detailsResponseEntity.roomId(),
                detailsResponseEntity.ticketsByChairs(),
                detailsResponseEntity.customerId(),
                detailsResponseEntity.totalPrice()
        );
    }

    @Override
    public CreatedOrderResponseModel createOrder(CreateOrderRequestModel toCreate) {
        CreateOrderRequestEntity createOrderRequestEntity = new CreateOrderRequestEntity(
                toCreate.customerId(),
                new CreateOrderRequestEntity.OrderItemRequestEntity(
                        toCreate.eventId(), toCreate.sessionId(), toCreate.roomId(), toCreate.ticketsByChairs()
                ),
                toCreate.totalPrice(),
                new CreateOrderRequestEntity.OrderPaymentRequestEntity(
                        toCreate.cardNumber(),
                        toCreate.cardHolderName(),
                        toCreate.exp(),
                        toCreate.banner()
                )
        );
        CreateOrderResponseEntity createOrderResponseEntity = ordersFeignClient.createOrder(createOrderRequestEntity);
        return new CreatedOrderResponseModel(createOrderResponseEntity.orderId(), createOrderResponseEntity.status());
    }

    @Override
    public void updateOrderStatus(Integer orderId, String status) {
        ordersFeignClient.updateOrderStatus(orderId, new UpdateOrderStatusRequestEntity(status));
    }

    @Override
    public void deleteOrderReservation(Integer reservationId) {
        ordersFeignClient.deleteOrderReservation(reservationId);
    }
}
