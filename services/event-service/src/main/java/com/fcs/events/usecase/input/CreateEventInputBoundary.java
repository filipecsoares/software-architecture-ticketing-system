package com.fcs.events.usecase.input;

import com.fcs.events.entity.Event;
import com.fcs.events.usecase.model.EventCreatedResponseModel;

public interface CreateEventInputBoundary {

    EventCreatedResponseModel execute(final Event toCreate);
}
