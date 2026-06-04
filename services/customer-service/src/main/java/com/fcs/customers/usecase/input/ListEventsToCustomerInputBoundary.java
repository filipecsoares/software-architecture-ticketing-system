package com.fcs.customers.usecase.input;

import com.fcs.customers.usecase.model.EventCustomerResponseModel;

import java.util.List;

public interface ListEventsToCustomerInputBoundary {
    List<EventCustomerResponseModel> execute();
}
