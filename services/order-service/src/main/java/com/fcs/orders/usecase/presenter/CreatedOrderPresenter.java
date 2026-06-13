package com.fcs.orders.usecase.presenter;

import com.fcs.orders.usecase.model.CreateOrderResponseModel;

public interface CreatedOrderPresenter {

    CreateOrderResponseModel prepareSuccessView(CreateOrderResponseModel responseModel);

    void prepareFailView(String error);
}
