package com.fcs.customers.adapter.presenter;

import com.fcs.customers.usecase.exception.BusinessException;
import com.fcs.customers.usecase.model.CustomerResponseModel;
import com.fcs.customers.usecase.presenter.CustomerPresenter;
import org.springframework.stereotype.Component;

@Component
public class CustomerResponseFormatter implements CustomerPresenter {

    @Override
    public CustomerResponseModel prepareSuccessView(CustomerResponseModel customerResponseModel) {
        return customerResponseModel;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
