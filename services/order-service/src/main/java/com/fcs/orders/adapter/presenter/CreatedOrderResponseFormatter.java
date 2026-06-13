package com.fcs.orders.adapter.presenter;

import com.fcs.orders.usecase.exception.BusinessException;
import com.fcs.orders.usecase.model.CreateOrderResponseModel;
import com.fcs.orders.usecase.presenter.CreatedOrderPresenter;
import org.springframework.stereotype.Component;

@Component
public class CreatedOrderResponseFormatter implements CreatedOrderPresenter {

    @Override
    public CreateOrderResponseModel prepareSuccessView(CreateOrderResponseModel responseModel) {
        return responseModel;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
