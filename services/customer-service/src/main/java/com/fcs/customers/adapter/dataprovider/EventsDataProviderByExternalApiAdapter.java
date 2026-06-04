package com.fcs.customers.adapter.dataprovider;

import com.fcs.customers.adapter.dataprovider.http.openfeign.EventsFeignClient;
import com.fcs.customers.adapter.dataprovider.http.openfeign.model.events.EventResponseEntity;
import com.fcs.customers.usecase.gateway.EventsGateway;
import com.fcs.customers.usecase.model.EventCustomerResponseModel;
import com.fcs.customers.usecase.model.SessionResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventsDataProviderByExternalApiAdapter implements EventsGateway {

    private final EventsFeignClient eventsFeignClient;

    @Override
    public List<EventCustomerResponseModel> get() {
        return eventsFeignClient.getEvents().stream()
                .map(eventResponseEntity -> new EventCustomerResponseModel(
                        eventResponseEntity.id(),
                        eventResponseEntity.name(),
                        eventResponseEntity.startDate(),
                        eventResponseEntity.endDate(),
                        eventResponseEntity.sessions().stream()
                                .map(sessionResponseEntity -> new SessionResponseModel(
                                        sessionResponseEntity.id(),
                                        sessionResponseEntity.name(),
                                        sessionResponseEntity.dateTime(),
                                        sessionResponseEntity.roomId(),
                                        sessionResponseEntity.ticketTypeIdsByQtd()

                                )).collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    @Override
    public EventCustomerResponseModel getById(Integer eventId) {
        EventResponseEntity eventResponseEntity = eventsFeignClient.getEventById(eventId);
        if (eventResponseEntity != null) {
            return new EventCustomerResponseModel(
                    eventResponseEntity.id(),
                    eventResponseEntity.name(),
                    eventResponseEntity.startDate(),
                    eventResponseEntity.endDate(),
                    eventResponseEntity.sessions().stream()
                            .map(sessionResponseEntity -> new SessionResponseModel(
                                    sessionResponseEntity.id(),
                                    sessionResponseEntity.name(),
                                    sessionResponseEntity.dateTime(),
                                    sessionResponseEntity.roomId(),
                                    sessionResponseEntity.ticketTypeIdsByQtd()

                            )).collect(Collectors.toList()));
        }
        return null;
    }
}
