package com.fcs.orders.adapter.presenter;

import com.fcs.orders.usecase.exception.BusinessException;
import com.fcs.orders.usecase.model.CreatedOrderReservationResponseModel;
import com.fcs.orders.usecase.presenter.CreatedOrderReservationPresenter;
import org.springframework.stereotype.Component;

@Component
public class CreatedOrderReservationResponseFormatter implements CreatedOrderReservationPresenter {

    @Override
    public CreatedOrderReservationResponseModel prepareSuccessView(CreatedOrderReservationResponseModel createdOrderReservationResponseModel) {
        return createdOrderReservationResponseModel;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
