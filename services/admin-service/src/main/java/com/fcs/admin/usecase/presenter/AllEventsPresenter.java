package com.fcs.admin.usecase.presenter;

import com.fcs.admin.usecase.model.EventResponseModel;

import java.util.List;

public interface AllEventsPresenter extends BasePresenter {

    List<EventResponseModel> prepareSuccessView(List<EventResponseModel> allEvents);
}
