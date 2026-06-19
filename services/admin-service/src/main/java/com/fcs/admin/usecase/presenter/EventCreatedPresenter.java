package com.fcs.admin.usecase.presenter;

import com.fcs.admin.usecase.model.EventCreatedResponseModel;

public interface EventCreatedPresenter extends BasePresenter {

    EventCreatedResponseModel prepareSuccessView(EventCreatedResponseModel eventCreatedResponseModel);
}
