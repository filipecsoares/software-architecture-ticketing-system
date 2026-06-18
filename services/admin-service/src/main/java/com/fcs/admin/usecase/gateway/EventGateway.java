package com.fcs.admin.usecase.gateway;

import com.fcs.admin.usecase.model.EventCreateModel;
import com.fcs.admin.usecase.model.EventCreatedResponseModel;
import com.fcs.admin.usecase.model.EventResponseModel;

import java.util.List;

public interface EventGateway {

    EventCreatedResponseModel create(EventCreateModel toCreate);

    void delete(Integer eventId);

    List<EventResponseModel> getAll();
}
