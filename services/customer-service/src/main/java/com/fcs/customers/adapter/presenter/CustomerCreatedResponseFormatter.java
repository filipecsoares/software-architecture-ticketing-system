package com.fcs.customers.adapter.presenter;

import com.fcs.customers.entity.Customer;
import com.fcs.customers.usecase.exception.BusinessException;
import com.fcs.customers.usecase.model.CustomerCreatedResponseModel;
import com.fcs.customers.usecase.presenter.CustomerCreatedPresenter;
import org.springframework.stereotype.Component;

@Component
public class CustomerCreatedResponseFormatter implements CustomerCreatedPresenter {

    @Override
    public CustomerCreatedResponseModel prepareSuccessView(Customer customer) {
        return new CustomerCreatedResponseModel(customer.getId());
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
