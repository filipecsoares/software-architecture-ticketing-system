package com.fcs.customers.adapter.presenter;

import com.fcs.customers.usecase.exception.BusinessException;
import com.fcs.customers.usecase.model.OrderDetailResponseModel;
import com.fcs.customers.usecase.presenter.GetCustomerOrdersPresenter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetCustomerOrdersResponseFormatter implements GetCustomerOrdersPresenter {

    @Override
    public List<OrderDetailResponseModel> prepareSuccessView(List<OrderDetailResponseModel> orderDetailResponseModels) {
        return orderDetailResponseModels;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
