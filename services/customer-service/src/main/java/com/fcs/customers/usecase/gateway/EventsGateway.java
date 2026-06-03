package com.fcs.customers.usecase.gateway;

import com.fcs.customers.usecase.model.EventCustomerResponseModel;

import java.util.List;

public interface EventsGateway {
    List<EventCustomerResponseModel> get();

    EventCustomerResponseModel getById(Integer eventId);
}
