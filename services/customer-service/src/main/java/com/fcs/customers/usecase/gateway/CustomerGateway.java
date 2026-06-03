package com.fcs.customers.usecase.gateway;

import com.fcs.customers.entity.Customer;
import com.fcs.customers.usecase.model.CustomerResponseModel;

import java.util.List;

public interface CustomerGateway {

    Integer create(Customer toCreate);

    void delete(Integer customerId);

    List<CustomerResponseModel> getAll();

    CustomerResponseModel getById(Integer customerId);

    boolean exists(String document);

    String getEncryptedPassword(String password);
}
