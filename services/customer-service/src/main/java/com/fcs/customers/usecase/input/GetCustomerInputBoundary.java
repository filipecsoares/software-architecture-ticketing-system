package com.fcs.customers.usecase.input;

import com.fcs.customers.usecase.model.CustomerResponseModel;

public interface GetCustomerInputBoundary {
    CustomerResponseModel execute(Integer customerId);
}
