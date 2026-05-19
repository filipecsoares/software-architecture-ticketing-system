package com.fcs.rooms.adapter.presenter;

import com.fcs.rooms.entity.Room;
import com.fcs.rooms.usecase.exception.BusinessException;
import com.fcs.rooms.usecase.model.RoomCreatedResponseModel;
import com.fcs.rooms.usecase.presenter.RoomCreatedPresenter;
import org.springframework.stereotype.Component;

@Component
public class RoomCreatedResponseFormatter implements RoomCreatedPresenter {

    @Override
    public RoomCreatedResponseModel prepareSuccessView(final Room room) {
        return new RoomCreatedResponseModel(room.getId());
    }

    @Override
    public void prepareFailView(final String error) {
        throw new BusinessException(error);
    }
}
