package com.fcs.admin.adapter.dataprovider;

import com.fcs.admin.adapter.dataprovider.http.openfeign.EventFeignClient;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.event.EventCreateRequestEntity;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.event.EventCreatedResponseEntity;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.event.EventResponseEntity;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.event.SessionRequestEntity;
import com.fcs.admin.usecase.gateway.EventGateway;
import com.fcs.admin.usecase.model.EventCreateModel;
import com.fcs.admin.usecase.model.EventCreatedResponseModel;
import com.fcs.admin.usecase.model.EventResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventDataProviderByExternalApiAdapter implements EventGateway {

    private final EventFeignClient eventFeignClient;

    @Override
    public EventCreatedResponseModel create(EventCreateModel toCreate) {
        EventCreateRequestEntity requestEntity = new EventCreateRequestEntity(toCreate.name(), toCreate.startDate(), toCreate.endDate(),
                toCreate.sessions().stream().map(sModel -> new SessionRequestEntity(sModel.name(),
                        sModel.startDateTime(),
                        sModel.endDateTime(),
                        sModel.roomId(),
                        sModel.ticketTypeIdsByQtd())).collect(Collectors.toList()));
        EventCreatedResponseEntity responseEntity = eventFeignClient.create(requestEntity);
        if (responseEntity != null) {
            return new EventCreatedResponseModel(responseEntity.createdId());
        }
        return null;
    }

    @Override
    public void delete(Integer eventId) {
        eventFeignClient.delete(eventId);
    }

    @Override
    public List<EventResponseModel> getAll() {
        List<EventResponseEntity> responsesEntity = eventFeignClient.getAll();
        if (responsesEntity != null) {
            return responsesEntity.stream().map(e -> new EventResponseModel(e.id(), e.name(), e.startDate(), e.endDate(), e.sessions()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
