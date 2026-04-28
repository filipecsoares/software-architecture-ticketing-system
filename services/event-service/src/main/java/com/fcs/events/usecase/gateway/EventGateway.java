package com.fcs.events.usecase.gateway;

import com.fcs.events.entity.Event;
import com.fcs.events.usecase.model.EventResponseModel;

import java.util.List;

public interface EventGateway {
    Integer create(final Event toCreate);
    boolean exists(final String name);
    List<EventResponseModel> getAll();
    EventResponseModel getById(final Integer eventId);
    void delete(final Integer eventId);
}
