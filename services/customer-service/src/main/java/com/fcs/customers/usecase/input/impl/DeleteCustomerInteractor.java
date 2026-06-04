package com.fcs.customers.usecase.input.impl;

import com.fcs.customers.usecase.gateway.CustomerGateway;
import com.fcs.customers.usecase.input.DeleteCustomerInputBoundary;
import com.fcs.customers.usecase.presenter.CustomerDeletedPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCustomerInteractor implements DeleteCustomerInputBoundary {

    private final CustomerDeletedPresenter customerDeletedPresenter;
    private final CustomerGateway customerGateway;

    @Override
    public void execute(Integer customerId) {
        try {
            customerGateway.delete(customerId);
        } catch (Exception e) {
            customerDeletedPresenter.prepareFailView("Ocorreu um erro interno ao excluir o cliente.");
        }
    }
}
