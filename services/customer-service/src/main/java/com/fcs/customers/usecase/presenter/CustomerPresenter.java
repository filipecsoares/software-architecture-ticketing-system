package com.fcs.customers.usecase.presenter;

import com.fcs.customers.usecase.model.CustomerResponseModel;

public interface CustomerPresenter {
    CustomerResponseModel prepareSuccessView(CustomerResponseModel customerResponseModel);

    void prepareFailView(String error);
}
