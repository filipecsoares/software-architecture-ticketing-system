package com.fcs.orders.adapter.presenter;

import com.fcs.orders.usecase.exception.BusinessException;
import com.fcs.orders.usecase.model.CreateOrderReservationResponseModel;
import com.fcs.orders.usecase.presenter.CreatedOrderReservationPresenter;
import org.springframework.stereotype.Component;

@Component
public class CreatedOrderReservationResponseFormatter implements CreatedOrderReservationPresenter {

    @Override
    public CreateOrderReservationResponseModel prepareSuccessView(CreateOrderReservationResponseModel createOrderReservationResponseModel) {
        return createOrderReservationResponseModel;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
