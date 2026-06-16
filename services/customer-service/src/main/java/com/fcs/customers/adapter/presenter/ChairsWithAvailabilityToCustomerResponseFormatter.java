package com.fcs.customers.adapter.presenter;

import com.fcs.customers.usecase.exception.BusinessException;
import com.fcs.customers.usecase.model.RoomDetailsWithChairsAvailabilityToCustomerResponseModel;
import com.fcs.customers.usecase.presenter.ChairsWithAvailabilityToCustomerPresenter;
import org.springframework.stereotype.Component;

@Component
public class ChairsWithAvailabilityToCustomerResponseFormatter implements ChairsWithAvailabilityToCustomerPresenter {

    @Override
    public RoomDetailsWithChairsAvailabilityToCustomerResponseModel prepareSuccessView(RoomDetailsWithChairsAvailabilityToCustomerResponseModel chairsAvailableToCustomerResponseModel) {
        return chairsAvailableToCustomerResponseModel;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
