package com.fcs.rooms.adapter.presenter;

import com.fcs.rooms.usecase.exception.BusinessException;
import com.fcs.rooms.usecase.presenter.RoomAllocatedPresenter;
import org.springframework.stereotype.Component;

@Component
public class RoomAllocatedResponseFormatter implements RoomAllocatedPresenter {

    @Override
    public void prepareFailView(final String error) {
        throw new BusinessException(error);
    }
}
