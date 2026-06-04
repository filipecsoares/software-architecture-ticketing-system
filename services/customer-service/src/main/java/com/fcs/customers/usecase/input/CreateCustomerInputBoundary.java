package com.fcs.customers.usecase.input;

import com.fcs.customers.entity.Customer;
import com.fcs.customers.usecase.model.CustomerCreatedResponseModel;

public interface CreateCustomerInputBoundary {
    CustomerCreatedResponseModel execute(Customer toCreate);
}
