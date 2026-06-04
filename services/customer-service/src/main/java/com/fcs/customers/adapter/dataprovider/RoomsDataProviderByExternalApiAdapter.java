package com.fcs.customers.adapter.dataprovider;

import com.fcs.customers.adapter.dataprovider.http.openfeign.RoomsFeignClient;
import com.fcs.customers.adapter.dataprovider.http.openfeign.model.rooms.RoomResponseEntity;
import com.fcs.customers.usecase.gateway.RoomsGateway;
import com.fcs.customers.usecase.model.PositionResponseModel;
import com.fcs.customers.usecase.model.RoomDetailsWithChairsAvailabilityToCustomerResponseModel;
import com.fcs.customers.usecase.model.SeatResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomsDataProviderByExternalApiAdapter implements RoomsGateway {

    private final RoomsFeignClient roomsFeignClient;

    @Override
    public RoomDetailsWithChairsAvailabilityToCustomerResponseModel getRoomById(Integer roomId) {

        RoomResponseEntity roomResponseEntity = roomsFeignClient.getRoomById(roomId);

        RoomDetailsWithChairsAvailabilityToCustomerResponseModel responseModel = new RoomDetailsWithChairsAvailabilityToCustomerResponseModel();

        responseModel.setRoomId(roomResponseEntity.getId());
        responseModel.setRoomName(roomResponseEntity.getName());

        List<SeatResponseModel> seatResponseModels = new ArrayList<>();
        roomResponseEntity.getSeats().forEach(seatResponseEntity -> {
            PositionResponseModel positionResponseModel = new PositionResponseModel(
                    seatResponseEntity.getPosition().id(),
                    seatResponseEntity.getPosition().row(),
                    seatResponseEntity.getPosition().num());
            seatResponseModels.add(new SeatResponseModel(
                    seatResponseEntity.getId(),
                    seatResponseEntity.getName(),
                    positionResponseModel,
                    true));
        });

        responseModel.setSeats(seatResponseModels);

        return responseModel;
    }
}
