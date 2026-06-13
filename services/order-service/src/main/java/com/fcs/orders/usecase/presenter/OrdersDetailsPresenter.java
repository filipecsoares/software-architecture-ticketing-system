package com.fcs.orders.usecase.presenter;

import com.fcs.orders.usecase.model.OrderDetailResponseModel;

import java.util.List;

public interface OrdersDetailsPresenter {

    List<OrderDetailResponseModel> prepareSuccessView(List<OrderDetailResponseModel> customerOrdersDetails);

    void prepareFailView(String s);
}
