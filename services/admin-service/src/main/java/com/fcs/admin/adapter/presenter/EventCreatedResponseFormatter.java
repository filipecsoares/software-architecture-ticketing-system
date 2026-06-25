package com.fcs.admin.adapter.presenter;

import com.fcs.admin.usecase.model.EventCreatedResponseModel;
import com.fcs.admin.usecase.presenter.EventCreatedPresenter;
import org.springframework.stereotype.Component;

@Component
public class EventCreatedResponseFormatter implements EventCreatedPresenter {

    @Override
    public EventCreatedResponseModel prepareSuccessView(EventCreatedResponseModel eventCreatedResponseModel) {
        return new EventCreatedResponseModel(eventCreatedResponseModel.createdId());
    }
}
