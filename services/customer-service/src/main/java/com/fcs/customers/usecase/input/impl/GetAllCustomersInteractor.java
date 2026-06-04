package com.fcs.customers.usecase.input.impl;

import com.fcs.customers.usecase.gateway.CustomerGateway;
import com.fcs.customers.usecase.input.GetAllCustomersInputBoundary;
import com.fcs.customers.usecase.model.CustomerResponseModel;
import com.fcs.customers.usecase.presenter.AllCustomersPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllCustomersInteractor implements GetAllCustomersInputBoundary {

    private final AllCustomersPresenter allCustomersPresenter;
    private final CustomerGateway customerGateway;

    @Override
    public List<CustomerResponseModel> execute() {
        List<CustomerResponseModel> allCustomers = new ArrayList<>();
        try {
            allCustomers = customerGateway.getAll();
        } catch (Exception e) {
            allCustomersPresenter.prepareFailView("Ocorreu um erro interno ao obter todos os clientes cadastrados.");
        }
        return allCustomersPresenter.prepareSuccessView(allCustomers);
    }
}
