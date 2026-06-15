package com.fcs.customers.usecase.input;

import com.fcs.customers.usecase.model.OrderDetailResponseModel;

import java.util.List;

public interface GetCustomerOrdersInputBoundary {
    List<OrderDetailResponseModel> execute(Integer customerId);
}
