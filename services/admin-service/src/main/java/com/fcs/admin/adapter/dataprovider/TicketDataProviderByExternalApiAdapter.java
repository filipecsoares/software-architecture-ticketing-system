package com.fcs.admin.adapter.dataprovider;

import com.fcs.admin.adapter.dataprovider.http.openfeign.TicketFeignClient;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.ticket.TicketCreateRequestEntity;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.ticket.TicketCreatedResponseEntity;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.ticket.TicketResponseEntity;
import com.fcs.admin.usecase.gateway.TicketGateway;
import com.fcs.admin.usecase.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TicketDataProviderByExternalApiAdapter implements TicketGateway {

    private final TicketFeignClient ticketFeignClient;

    @Override
    public TicketCreatedResponseModel create(CreateTicketModel toCreate) {
        TicketCreateRequestEntity requestEntity = new TicketCreateRequestEntity(toCreate.name(),
                toCreate.ticketType(),
                toCreate.ticketUnitPrice());
        TicketCreatedResponseEntity responseEntity = ticketFeignClient.create(requestEntity);
        if (responseEntity != null) {
            return new TicketCreatedResponseModel(responseEntity.getCreatedId());
        }
        return null;
    }

    @Override
    public List<TicketResponseModel> getAll() {
        List<TicketResponseEntity> responsesEntity = ticketFeignClient.getAll();
        if (responsesEntity != null) {
            return responsesEntity.stream().map(t -> new TicketResponseModel(t.id(),
                    t.name(),
                    TicketTypeResponseModel.valueOf(t.type().toString()),
                    new TicketUnitPriceResponseModel(t.unitPrice().value(), CurrencyResponseModel.valueOf(t.unitPrice().currency().toString())))
            ).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void delete(Integer ticketId) {
        ticketFeignClient.delete(ticketId);
    }
}
