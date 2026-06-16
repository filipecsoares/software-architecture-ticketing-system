package com.fcs.customers.adapter.presenter;

import com.fcs.customers.usecase.exception.BusinessException;
import com.fcs.customers.usecase.presenter.CreateCustomerOrderReservationPresenter;
import org.springframework.stereotype.Component;

@Component
public class CreateCustomerOrderReservationResponseFormatter implements CreateCustomerOrderReservationPresenter {

    @Override
    public Integer prepareSuccessView(Integer reservationId) {
        return reservationId;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
