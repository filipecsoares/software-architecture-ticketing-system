package com.fcs.admin.usecase.presenter;

import com.fcs.admin.usecase.model.RoomCreatedResponseModel;

public interface RoomCreatedPresenter extends BasePresenter {

    RoomCreatedResponseModel prepareSuccessView(RoomCreatedResponseModel roomCreatedResponseModel);
}
