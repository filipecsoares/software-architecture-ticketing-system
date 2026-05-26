package com.fcs.orders.usecase.presenter;

import com.fcs.orders.usecase.model.CreatedOrderReservationResponseModel;

public interface CreatedOrderReservationPresenter {
    CreatedOrderReservationResponseModel prepareSuccessView(CreatedOrderReservationResponseModel createdOrderReservationResponseModel);

    void prepareFailView(String error);
}
