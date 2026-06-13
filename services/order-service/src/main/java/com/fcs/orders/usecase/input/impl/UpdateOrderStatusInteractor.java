package com.fcs.orders.usecase.input.impl;

import com.fcs.orders.entity.vo.OrderStatus;
import com.fcs.orders.usecase.gateway.OrderGateway;
import com.fcs.orders.usecase.input.UpdateOrderStatusInputBoundary;
import com.fcs.orders.usecase.presenter.UpdatedOrderStatusPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateOrderStatusInteractor implements UpdateOrderStatusInputBoundary {

    private final UpdatedOrderStatusPresenter presenter;
    private final OrderGateway gateway;

    @Override
    public void execute(Integer orderId, OrderStatus status) {
        try {
            gateway.update(orderId, status);
        } catch (Exception e) {
            presenter.prepareFailView("Ocorreu um erro ao atualizar o status do pedido. Detalhe: " + e.getMessage());
        }
    }
}
