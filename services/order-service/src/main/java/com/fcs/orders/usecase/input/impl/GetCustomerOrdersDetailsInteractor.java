package com.fcs.orders.usecase.input.impl;

import com.fcs.orders.usecase.gateway.OrderGateway;
import com.fcs.orders.usecase.input.GetCustomerOrdersDetailsInputBoundary;
import com.fcs.orders.usecase.model.OrderDetailResponseModel;
import com.fcs.orders.usecase.presenter.OrdersDetailsPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCustomerOrdersDetailsInteractor implements GetCustomerOrdersDetailsInputBoundary {

    private final OrderGateway gateway;
    private final OrdersDetailsPresenter presenter;

    @Override
    public List<OrderDetailResponseModel> execute(Integer customerId) {
        try {
            return presenter.prepareSuccessView(gateway.getCustomerOrdersDetails(customerId));
        } catch (Exception e) {
            presenter.prepareFailView("Ocorreu um erro interno ao obter os pedidos do cliente. Detalhe: " + e.getMessage());
        }
        return null;
    }
}
