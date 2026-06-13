package com.fcs.orders.adapter.presenter;

import com.fcs.orders.usecase.exception.BusinessException;
import com.fcs.orders.usecase.presenter.DeletedOrderReservationPresenter;
import org.springframework.stereotype.Component;

@Component
public class DeletedOrderReservationResponseFormatter implements DeletedOrderReservationPresenter {

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
