package com.fcs.customers.usecase.presenter;

import com.fcs.customers.entity.Customer;
import com.fcs.customers.usecase.model.CustomerCreatedResponseModel;

public interface CustomerCreatedPresenter {
    CustomerCreatedResponseModel prepareSuccessView(Customer customer);

    void prepareFailView(String error);
}
