package com.fcs.orders.usecase.input.impl;

import com.fcs.orders.entity.Order;
import com.fcs.orders.entity.vo.OrderStatus;
import com.fcs.orders.usecase.gateway.OrderGateway;
import com.fcs.orders.usecase.input.CreateOrderInputBoundary;
import com.fcs.orders.usecase.model.CreateOrderRequestModel;
import com.fcs.orders.usecase.model.CreateOrderResponseModel;
import com.fcs.orders.usecase.presenter.CreatedOrderPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderInteractor implements CreateOrderInputBoundary {

    private final CreatedOrderPresenter presenter;
    private final OrderGateway gateway;

    @Override
    public CreateOrderResponseModel execute(CreateOrderRequestModel requestModel) {
        try {
            Order toCreate = new Order();
            toCreate.setItem(requestModel.item());
            toCreate.setCustomerId(requestModel.customerId());
            toCreate.setTotalPrice(requestModel.totalPrice());
            toCreate.setPayment(requestModel.payment());
            toCreate.setStatus(OrderStatus.PENDING);
            if (toCreate.isValidToCreate()) {
                return presenter.prepareSuccessView(gateway.createOrder(toCreate));
            } else {
                presenter.prepareFailView("Não foi possível criar o pedido. Verique os dados do pedido.");
            }
        } catch (Exception e) {
            presenter.prepareFailView("Ocorreu um erro interno ao criar pedido. Detalhe: " + e.getMessage());
        }
        return null;
    }
}
