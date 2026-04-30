package com.fcs.events.adapter.dataprovider;

import com.fcs.events.adapter.dataprovider.http.openfeign.RoomFeignClient;
import com.fcs.events.adapter.dataprovider.http.openfeign.model.room.AllocateRequestEntity;
import com.fcs.events.adapter.dataprovider.http.openfeign.model.room.RoomResponseEntity;
import com.fcs.events.usecase.gateway.RoomGateway;
import com.fcs.events.usecase.model.RoomModel;
import feign.FeignException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class RoomDataProviderByExternalApiAdapter implements RoomGateway {

    private final RoomFeignClient roomFeignClient;
    private final Logger LOGGER = Logger.getLogger(RoomDataProviderByExternalApiAdapter.class.getName());

    public RoomDataProviderByExternalApiAdapter(final RoomFeignClient roomFeignClient) {
        this.roomFeignClient = roomFeignClient;
    }

    @Override
    public RoomModel findBy(final Integer id, final LocalDateTime requestUseDateTime) {
        final RoomResponseEntity roomResponseEntity = roomFeignClient.getRoom(id);
        if (roomResponseEntity != null) {
            if (roomResponseEntity.getAllocations() != null) {
                boolean isAvailable = roomResponseEntity.getAllocations().stream()
                        .noneMatch(allocationResponseEntity ->
                                requestUseDateTime.isAfter(allocationResponseEntity.getStartDateTime()) &&
                                        requestUseDateTime.isBefore(allocationResponseEntity.getEndDateTime())
                        );
                if (!isAvailable) {
                    return new RoomModel(roomResponseEntity.getName(), roomResponseEntity.getSeats().size(), true, false);
                }
            }
            return new RoomModel(roomResponseEntity.getName(), roomResponseEntity.getSeats().size(), true, true);
        }
        return new RoomModel("", 0,false, false);
    }

    @Override
    public boolean allocate(final Integer roomId, final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        final AllocateRequestEntity requestEntity = new AllocateRequestEntity(startDateTime, endDateTime);
        try {
            roomFeignClient.allocate(roomId, requestEntity);
            return true;
        } catch (FeignException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }
}
