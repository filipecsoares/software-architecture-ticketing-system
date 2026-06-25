package com.fcs.admin.adapter.presenter;

import com.fcs.admin.usecase.model.RoomCreatedResponseModel;
import com.fcs.admin.usecase.presenter.RoomCreatedPresenter;
import org.springframework.stereotype.Component;

@Component
public class RoomCreatedResponseFormatter implements RoomCreatedPresenter {

    @Override
    public RoomCreatedResponseModel prepareSuccessView(RoomCreatedResponseModel roomCreatedResponseModel) {
        return new RoomCreatedResponseModel(roomCreatedResponseModel.createdId());
    }
}
