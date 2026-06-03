package com.fcs.customers.usecase.presenter;

import com.fcs.customers.usecase.model.CustomerResponseModel;

import java.util.List;

public interface AllCustomersPresenter {
    List<CustomerResponseModel> prepareSuccessView(List<CustomerResponseModel> allCustomers);

    void prepareFailView(String error);
}
