package com.fcs.events.usecase.presenter;

import com.fcs.events.entity.Event;
import com.fcs.events.usecase.model.EventCreatedResponseModel;

public interface EventCreatedPresenter {
    EventCreatedResponseModel prepareSuccessView(final Event event);
    void prepareFailView(String error);
}
