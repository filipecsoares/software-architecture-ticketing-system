package com.fcs.customers.usecase.presenter;

import com.fcs.customers.usecase.model.EventCustomerResponseModel;

import java.util.List;

public interface EventsToCustomerPresenter {
    List<EventCustomerResponseModel> prepareSuccessView(List<EventCustomerResponseModel> eventsCustomerResponseModel);

    void prepareFailView(String error);
}
