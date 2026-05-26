package com.fcs.orders.adapter.presenter;

import com.fcs.orders.usecase.exception.BusinessException;
import com.fcs.orders.usecase.model.UnavailableChairsResponseModel;
import com.fcs.orders.usecase.presenter.UnavailableChairsPresenter;
import org.springframework.stereotype.Component;

@Component
public class UnavailableChairsResponseFormatter implements UnavailableChairsPresenter {

    @Override
    public UnavailableChairsResponseModel prepareSuccessView(UnavailableChairsResponseModel unavailableChairsResponseModel) {
        return unavailableChairsResponseModel;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
