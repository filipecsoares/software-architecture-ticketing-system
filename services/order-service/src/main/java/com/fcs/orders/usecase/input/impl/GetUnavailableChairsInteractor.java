package com.fcs.orders.usecase.input.impl;

import com.fcs.orders.usecase.gateway.OrderGateway;
import com.fcs.orders.usecase.input.GetUnavailableChairsInputBoundary;
import com.fcs.orders.usecase.model.UnavailableChairsResponseModel;
import com.fcs.orders.usecase.presenter.UnavailableChairsPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUnavailableChairsInteractor implements GetUnavailableChairsInputBoundary {

    private final UnavailableChairsPresenter unavailableChairsPresenter;
    private final OrderGateway orderGateway;

    @Override
    public UnavailableChairsResponseModel execute(Integer sessionId) {
        try {
            return unavailableChairsPresenter.prepareSuccessView(orderGateway.getUnavailableChairs(sessionId));
        } catch (Exception e) {
            unavailableChairsPresenter.prepareFailView("Ocorreu um erro interno ao obter as cadeiras ocupadas para a sessao: '" + sessionId + "'");
        }
        return null;
    }
}
