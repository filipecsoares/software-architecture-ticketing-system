package com.fcs.orders.adapter.presenter;

import com.fcs.orders.usecase.exception.BusinessException;
import com.fcs.orders.usecase.model.OrderReservationDetailsResponseModel;
import com.fcs.orders.usecase.presenter.OrderReservationDetailsPresenter;
import org.springframework.stereotype.Component;

@Component
public class OrderReservationDetailsResponseFormatter implements OrderReservationDetailsPresenter {

    @Override
    public OrderReservationDetailsResponseModel prepareSuccessView(OrderReservationDetailsResponseModel responseModel) {
        return responseModel;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
