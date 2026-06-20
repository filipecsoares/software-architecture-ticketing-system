package com.fcs.admin.adapter.dataprovider;

import com.fcs.admin.adapter.dataprovider.http.openfeign.RoomFeignClient;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.room.RoomCreateRequestEntity;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.room.RoomCreatedResponseEntity;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.room.RoomResponseEntity;
import com.fcs.admin.usecase.gateway.RoomGateway;
import com.fcs.admin.usecase.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoomDataProviderByExternalApiAdapter implements RoomGateway {

    private final RoomFeignClient roomFeignClient;

    @Override
    public RoomCreatedResponseModel create(RoomCreateModel toCreate) {
        RoomCreateRequestEntity requestEntity = new RoomCreateRequestEntity(toCreate.getName(), toCreate.getSeats());
        RoomCreatedResponseEntity responseEntity = roomFeignClient.create(requestEntity);
        if (responseEntity != null) {
            return new RoomCreatedResponseModel(responseEntity.createdId());
        }
        return null;
    }

    @Override
    public void delete(Integer roomId) {
        roomFeignClient.delete(roomId);
    }

    @Override
    public List<RoomResponseModel> getAll() {
        List<RoomResponseEntity> responsesEntity = roomFeignClient.getAll();
        if (responsesEntity != null) {
            return responsesEntity.stream().map(r -> new RoomResponseModel(r.id(),
                    r.name(),
                    r.seats().stream().map(s -> new SeatResponseModel(s.id(), s.name(), new PositionResponseModel(s.position().id(), s.position().row(), s.position().num()))).collect(Collectors.toList()),
                    r.allocations().stream().map(a -> new AllocationResponseModel(a.id(), a.startDateTime(), a.endDateTime())).collect(Collectors.toList()))
            ).collect(Collectors.toList());
        }
        return null;
    }
}
