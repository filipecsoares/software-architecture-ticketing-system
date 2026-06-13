package com.fcs.orders.usecase.presenter;

import com.fcs.orders.usecase.model.CreateOrderReservationResponseModel;

public interface CreatedOrderReservationPresenter {
    CreateOrderReservationResponseModel prepareSuccessView(CreateOrderReservationResponseModel createOrderReservationResponseModel);

    void prepareFailView(String error);
}
