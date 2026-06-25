package com.fcs.admin.adapter.presenter;

import com.fcs.admin.usecase.model.RoomResponseModel;
import com.fcs.admin.usecase.presenter.AllRoomsPresenter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllRoomsResponseFormatter implements AllRoomsPresenter {

    @Override
    public List<RoomResponseModel> prepareSuccessView(List<RoomResponseModel> allRooms) {
        return allRooms;
    }

}
