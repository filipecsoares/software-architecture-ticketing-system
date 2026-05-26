package com.fcs.orders.usecase.input.impl;

import com.fcs.orders.usecase.gateway.OrderGateway;
import com.fcs.orders.usecase.input.VerifyIfOnlyOneOrderIsInProgressInputBoundary;
import com.fcs.orders.usecase.presenter.OnlyOneOrderInProgressPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyIfOnlyOneOrderIsInProgressInteractor implements VerifyIfOnlyOneOrderIsInProgressInputBoundary {

    private final OnlyOneOrderInProgressPresenter onlyOneOrderInProgressPresenter;
    private final OrderGateway orderGateway;

    @Override
    public boolean execute(Integer customerId) {
        try {
            return orderGateway.hasOrderInProgress(customerId);
        } catch (Exception e) {
            onlyOneOrderInProgressPresenter.prepareFailView("Ocorreu um erro interno ao obter a informacao se o cliente possui outro pedido em andamento. Detalhe: " + e.getMessage());
        }
        return false;
    }
}
