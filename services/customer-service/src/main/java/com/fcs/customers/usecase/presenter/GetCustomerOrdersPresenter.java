package com.fcs.customers.usecase.presenter;

import com.fcs.customers.usecase.model.OrderDetailResponseModel;

import java.util.List;

public interface GetCustomerOrdersPresenter {
    List<OrderDetailResponseModel> prepareSuccessView(List<OrderDetailResponseModel> orderDetailResponseModels);

    void prepareFailView(String error);
}
