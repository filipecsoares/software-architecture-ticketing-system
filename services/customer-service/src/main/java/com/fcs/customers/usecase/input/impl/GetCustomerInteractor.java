package com.fcs.customers.usecase.input.impl;

import com.fcs.customers.usecase.gateway.CustomerGateway;
import com.fcs.customers.usecase.input.GetCustomerInputBoundary;
import com.fcs.customers.usecase.model.CustomerResponseModel;
import com.fcs.customers.usecase.presenter.CustomerPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCustomerInteractor implements GetCustomerInputBoundary {

    private final CustomerPresenter customerPresenter;
    private final CustomerGateway customerGateway;

    @Override
    public CustomerResponseModel execute(Integer customerId) {
        try {
            return customerPresenter.prepareSuccessView(customerGateway.getById(customerId));
        } catch (Exception e) {
            customerPresenter.prepareFailView("Ocorreu um erro interno ao obter os dados do cliente.");
        }
        return null;
    }
}
