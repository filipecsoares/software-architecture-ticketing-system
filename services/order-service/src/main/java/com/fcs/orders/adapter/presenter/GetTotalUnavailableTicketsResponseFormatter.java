package com.fcs.orders.adapter.presenter;

import com.fcs.orders.usecase.exception.BusinessException;
import com.fcs.orders.usecase.presenter.TotalUnavailableTicketsPresenter;
import org.springframework.stereotype.Component;

@Component
public class GetTotalUnavailableTicketsResponseFormatter implements TotalUnavailableTicketsPresenter {
    @Override
    public Integer prepareSuccessView(Integer total) {
        return total;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
