package com.fcs.orders.usecase.presenter;

import com.fcs.orders.usecase.model.OrderReservationDetailsResponseModel;

public interface OrderReservationDetailsPresenter {

    OrderReservationDetailsResponseModel prepareSuccessView(OrderReservationDetailsResponseModel responseModel);

    void prepareFailView(String error);
}
