package com.fcs.customers.usecase.input.impl;

import com.fcs.customers.entity.Customer;
import com.fcs.customers.usecase.gateway.CustomerGateway;
import com.fcs.customers.usecase.input.CreateCustomerInputBoundary;
import com.fcs.customers.usecase.model.CustomerCreatedResponseModel;
import com.fcs.customers.usecase.presenter.CustomerCreatedPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCustomerInteractor implements CreateCustomerInputBoundary {

    private final CustomerGateway customerGateway;
    private final CustomerCreatedPresenter customerCreatedPresenter;

    @Override
    public CustomerCreatedResponseModel execute(Customer toCreate) {
        if (!toCreate.isValid())
            customerCreatedPresenter.prepareFailView("Verifique que todas as informações do cliente estão preenchidas corretamente.");
        boolean customerAlreadyExists = customerGateway.exists(toCreate.getDocument().getCode());
        if (customerAlreadyExists)
            customerCreatedPresenter.prepareFailView("Já existe um cliente cadastrado com o documento '" + toCreate.getDocument().getCode() + "'");
        /*String encryptedPassword = customerGateway.getEncryptedPassword(toCreate.getPassword());
        toCreate.setPassword(encryptedPassword);*/
        Integer createdId = customerGateway.create(toCreate);
        toCreate.setId(createdId);
        return customerCreatedPresenter.prepareSuccessView(toCreate);
    }
}
