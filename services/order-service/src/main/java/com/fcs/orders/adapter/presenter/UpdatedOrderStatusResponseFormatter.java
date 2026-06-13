package com.fcs.orders.adapter.presenter;

import com.fcs.orders.usecase.exception.BusinessException;
import com.fcs.orders.usecase.presenter.UpdatedOrderStatusPresenter;
import org.springframework.stereotype.Component;

@Component
public class UpdatedOrderStatusResponseFormatter implements UpdatedOrderStatusPresenter {

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
