package com.fcs.customers.usecase.input;

import com.fcs.customers.usecase.model.CustomerResponseModel;

import java.util.List;

public interface GetAllCustomersInputBoundary {
    List<CustomerResponseModel> execute();
}
