package com.fcs.customers.usecase.input.impl;

import com.fcs.customers.usecase.gateway.EventsGateway;
import com.fcs.customers.usecase.gateway.OrdersGateway;
import com.fcs.customers.usecase.gateway.RoomsGateway;
import com.fcs.customers.usecase.gateway.TicketsGateway;
import com.fcs.customers.usecase.input.GetCustomerOrdersInputBoundary;
import com.fcs.customers.usecase.model.EventCustomerResponseModel;
import com.fcs.customers.usecase.model.OrderDetailResponseModel;
import com.fcs.customers.usecase.model.OrderResponseModel;
import com.fcs.customers.usecase.model.SessionResponseModel;
import com.fcs.customers.usecase.presenter.GetCustomerOrdersPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetCustomerOrdersInteractor implements GetCustomerOrdersInputBoundary {

    private final OrdersGateway ordersGateway;
    private final EventsGateway eventsGateway;
    private final RoomsGateway roomsGateway;
    private final TicketsGateway ticketsGateway;
    private final GetCustomerOrdersPresenter presenter;

    @Override
    public List<OrderDetailResponseModel> execute(Integer customerId) {
        List<OrderDetailResponseModel> orderDetailResponseModels = new ArrayList<>();
        try {
            List<OrderResponseModel> orderResponseModels = ordersGateway.getAllByCustomerId(customerId);
            orderResponseModels.forEach(orderResponseModel -> {
                createOrderDetailResponseModel(orderDetailResponseModels, orderResponseModel);
            });
            return presenter.prepareSuccessView(orderDetailResponseModels);
        } catch (Exception e) {
            presenter.prepareFailView("Ocorreu um erro interno ao obter os pedidos do cliente.");
        }
        return null;
    }

    private void createOrderDetailResponseModel(List<OrderDetailResponseModel> orderDetailResponseModels, OrderResponseModel orderResponseModel) {
        EventCustomerResponseModel event = eventsGateway.getById(orderResponseModel.eventId());

        SessionResponseModel session = event.sessions()
                .stream()
                .filter(s -> s.id().equals(orderResponseModel.sessionId()))
                .toList().get(0);

        String eventName = event.name();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedSessionDate = session.dateTime().format(formatter);
        String sessionName = session.name() + " - " + formattedSessionDate;
        String roomName = roomsGateway.getRoomById(session.roomId()).getRoomName();

        Map<String, List<String>> ticketNamesByChairs = new HashMap<>();
        ticketsGateway.getBy(orderResponseModel.ticketsByChairs().keySet().stream().toList())
                .forEach(ticket -> ticketNamesByChairs.put(ticket.name(), orderResponseModel.ticketsByChairs().get(ticket.id())) );

        orderDetailResponseModels.add(new OrderDetailResponseModel(
                orderResponseModel.orderId(),
                eventName,
                sessionName,
                roomName,
                ticketNamesByChairs,
                orderResponseModel.totalPrice(),
                orderResponseModel.cardNumber(),
                orderResponseModel.cardHolderName(),
                orderResponseModel.exp(),
                orderResponseModel.cardBanner(),
                orderResponseModel.status()
        ));
    }
}
