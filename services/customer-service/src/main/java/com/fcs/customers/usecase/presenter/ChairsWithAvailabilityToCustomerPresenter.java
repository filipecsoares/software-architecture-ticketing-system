package com.fcs.customers.usecase.presenter;

import com.fcs.customers.usecase.model.RoomDetailsWithChairsAvailabilityToCustomerResponseModel;

public interface ChairsWithAvailabilityToCustomerPresenter {
    RoomDetailsWithChairsAvailabilityToCustomerResponseModel prepareSuccessView(RoomDetailsWithChairsAvailabilityToCustomerResponseModel chairsAvailableToCustomerResponseModel);

    void prepareFailView(String error);
}
