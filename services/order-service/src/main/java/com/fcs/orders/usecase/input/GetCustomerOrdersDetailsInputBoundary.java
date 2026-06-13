package com.fcs.orders.usecase.input;

import com.fcs.orders.usecase.model.OrderDetailResponseModel;

import java.util.List;

public interface GetCustomerOrdersDetailsInputBoundary {

    List<OrderDetailResponseModel> execute(Integer customerId);
}
