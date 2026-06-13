package com.fcs.orders.adapter.presenter;

import com.fcs.orders.usecase.exception.BusinessException;
import com.fcs.orders.usecase.model.OrderDetailResponseModel;
import com.fcs.orders.usecase.presenter.OrdersDetailsPresenter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrdersDetailsResponseFormatter implements OrdersDetailsPresenter {
    @Override
    public List<OrderDetailResponseModel> prepareSuccessView(List<OrderDetailResponseModel> customerOrdersDetails) {
        return customerOrdersDetails;
    }

    @Override
    public void prepareFailView(String s) {
        throw new BusinessException(s);
    }
}
