package com.fcs.orders.adapter.presenter;

import com.fcs.orders.usecase.exception.BusinessException;
import com.fcs.orders.usecase.presenter.OnlyOneOrderInProgressPresenter;
import org.springframework.stereotype.Component;

@Component
public class OnlyOneOrderInProgressResponseFormatter implements OnlyOneOrderInProgressPresenter {

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
