package com.fcs.rooms.adapter.presenter;

import com.fcs.rooms.usecase.exception.BusinessException;
import com.fcs.rooms.usecase.presenter.RoomDeletedPresenter;
import org.springframework.stereotype.Component;

@Component
public class RoomDeletedResponseFormatter implements RoomDeletedPresenter {

    @Override
    public void prepareFailView(final String error) {
        throw new BusinessException(error);
    }
}
