package com.fcs.events.usecase.input.impl;

import com.fcs.events.entity.Event;
import com.fcs.events.usecase.input.CreateEventInputBoundary;
import com.fcs.events.usecase.model.EventCreatedResponseModel;
import org.springframework.stereotype.Service;

@Service
public class CreateEventInteractor implements CreateEventInputBoundary {

    @Override
    public EventCreatedResponseModel execute(final Event toCreate) {
        return null;
    }
}
