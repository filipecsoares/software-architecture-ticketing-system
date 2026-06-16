package com.fcs.customers.adapter.presenter;

import com.fcs.customers.usecase.exception.BusinessException;
import com.fcs.customers.usecase.model.CustomerResponseModel;
import com.fcs.customers.usecase.presenter.AllCustomersPresenter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllCustomersResponseFormatter implements AllCustomersPresenter {

    @Override
    public List<CustomerResponseModel> prepareSuccessView(List<CustomerResponseModel> allCustomers) {
        return allCustomers;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
