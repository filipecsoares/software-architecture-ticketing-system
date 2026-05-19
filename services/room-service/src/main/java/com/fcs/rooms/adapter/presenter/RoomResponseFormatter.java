package com.fcs.rooms.adapter.presenter;

import com.fcs.rooms.usecase.exception.BusinessException;
import com.fcs.rooms.usecase.model.RoomResponseModel;
import com.fcs.rooms.usecase.presenter.RoomPresenter;
import org.springframework.stereotype.Component;

@Component
public class RoomResponseFormatter implements RoomPresenter {

    @Override
    public RoomResponseModel prepareSuccessView(final RoomResponseModel roomResponseModel) {
        return roomResponseModel;
    }

    @Override
    public void prepareFailView(final String error) {
        throw new BusinessException(error);
    }
}
