package com.fcs.orders.usecase.input;

import com.fcs.orders.usecase.model.CreateOrderRequestModel;
import com.fcs.orders.usecase.model.CreateOrderResponseModel;

public interface CreateOrderInputBoundary {

    CreateOrderResponseModel execute(CreateOrderRequestModel requestModel);
}
