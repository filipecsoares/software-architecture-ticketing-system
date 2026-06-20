package com.fcs.admin.usecase.input;

import com.fcs.admin.usecase.model.EventCreateModel;
import com.fcs.admin.usecase.model.EventCreatedResponseModel;

public interface CreateEventInputBoundary {

    EventCreatedResponseModel execute(EventCreateModel toCreate);
}
