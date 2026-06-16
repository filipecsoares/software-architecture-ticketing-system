package com.fcs.customers.adapter.presenter;

import com.fcs.customers.usecase.exception.BusinessException;
import com.fcs.customers.usecase.presenter.CustomerDeletedPresenter;
import org.springframework.stereotype.Component;

@Component
public class CustomerDeletedResponseFormatter implements CustomerDeletedPresenter {

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
