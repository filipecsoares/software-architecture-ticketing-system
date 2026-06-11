package com.fcs.payment.usecase.input.impl;

import com.fcs.payment.usecase.exception.BusinessException;
import com.fcs.payment.usecase.gateway.DecryptPaymentRequestDataGateway;
import com.fcs.payment.usecase.gateway.OrderGateway;
import com.fcs.payment.usecase.gateway.PaymentProcessGateway;
import com.fcs.payment.usecase.input.ProcessPaymentRequestInputBoundary;
import com.fcs.payment.usecase.model.CreateOrderRequestModel;
import com.fcs.payment.usecase.model.CreatedOrderResponseModel;
import com.fcs.payment.usecase.model.OrderReservationDetailsResponseModel;
import com.fcs.payment.usecase.model.PaymentProcessedResponseModel;
import com.fcs.payment.usecase.presenter.PaymentProcessPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProcessPaymentRequestInteractor implements ProcessPaymentRequestInputBoundary {

    private final DecryptPaymentRequestDataGateway decryptPaymentRequestDataGateway;
    private final PaymentProcessGateway paymentProcessGateway;
    private final PaymentProcessPresenter paymentProcessPresenter;
    private final OrderGateway orderGateway;

    @Override
    public PaymentProcessedResponseModel execute(Integer reservationId, String encryptedPaymentData) {
        try {

            // Obtem os detalhes da reserva
            OrderReservationDetailsResponseModel orderReservationDetails = orderGateway.getReservationDetails(reservationId);

            // Recupera as informacoes de pagamento
            String decryptedPaymentData = decryptPaymentRequestDataGateway.decrypt(encryptedPaymentData);
            String cardNumber = decryptedPaymentData.split("PaymentData")[1].split("cardNumber=")[1].split(",")[0];
            String cvv = decryptedPaymentData.split("PaymentData")[1].split("cvv=")[1].split(",")[0];
            String cardHolderName = decryptedPaymentData.split("PaymentData")[1].split("cardHolderName=")[1].split(",")[0];
            String exp = decryptedPaymentData.split("PaymentData")[1].split("exp=")[1].split(",")[0];
            String banner = decryptedPaymentData.split("PaymentData")[1].split("banner=")[1].split(",")[0].split("]")[0];
            Double totalPrice = orderReservationDetails.totalPrice();

            // Cria o pedido com o status "PENDING"
            CreatedOrderResponseModel createdOrderResponseModel = createOrderWithPendingStatus(orderReservationDetails, cardNumber, cardHolderName, exp, banner, totalPrice);

            if (!("PENDING".equals(createdOrderResponseModel.status()) && createdOrderResponseModel.orderId() != null))
                paymentProcessPresenter.prepareFailView("Ocorreu ao criar o pedido. O pagamento nao sera realizado.");

            // Realiza o pagamento
            makePaymentAndUpdateOrderStatus(cardNumber, cvv, cardHolderName, exp, banner, totalPrice, createdOrderResponseModel.orderId());

            return paymentProcessPresenter.prepareSuccessView(new PaymentProcessedResponseModel(true));

        } catch (Exception e) {
            paymentProcessPresenter.prepareFailView("Ocorreu um erro no fluxo de processamento do pagamento. Causa: " + e.getMessage());
        } finally {
            // Deleta a reserva ja nao mais necessaria
            orderGateway.deleteOrderReservation(reservationId);
        }
        return null;
    }

    private void makePaymentAndUpdateOrderStatus(String cardNumber, String cvv, String cardHolderName, String exp, String banner, Double totalPrice, Integer orderId) {
        boolean processed = paymentProcessGateway.process(totalPrice, cardNumber, cvv, cardHolderName, exp, banner);
        if (!processed) {
            orderGateway.updateOrderStatus(orderId,"DENIED");
            throw new BusinessException("O pagamento nao foi autorizado");
        }
        orderGateway.updateOrderStatus(orderId,"PAID");
    }

    private CreatedOrderResponseModel createOrderWithPendingStatus(OrderReservationDetailsResponseModel orderReservationDetails, String cardNumber, String cardHolderName, String exp, String banner, Double totalPrice) {
        return orderGateway.createOrder(new CreateOrderRequestModel(
                orderReservationDetails.customerId(),
                orderReservationDetails.eventId(),
                orderReservationDetails.sessionId(),
                orderReservationDetails.roomId(),
                orderReservationDetails.ticketsByChairs(),
                totalPrice,
                Long.valueOf(cardNumber),
                cardHolderName,
                LocalDate.parse(exp),
                banner));
    }
}
