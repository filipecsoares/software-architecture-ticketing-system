package com.fcs.admin.usecase.input;

import com.fcs.admin.usecase.model.EventResponseModel;

import java.util.List;

public interface GetAllEventsInputBoundary {

    List<EventResponseModel> execute();
}
