package com.fcs.customers.adapter.dataprovider;

import com.fcs.customers.adapter.dataprovider.http.openfeign.TicketsFeignClient;
import com.fcs.customers.adapter.dataprovider.http.openfeign.model.tickets.TicketResponseEntity;
import com.fcs.customers.usecase.gateway.TicketsGateway;
import com.fcs.customers.usecase.model.CurrencyResponseModel;
import com.fcs.customers.usecase.model.TicketCustomerResponseModel;
import com.fcs.customers.usecase.model.TicketTypeResponseModel;
import com.fcs.customers.usecase.model.TicketUnitPriceResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TicketsDataProviderByExternalApiAdapter implements TicketsGateway {

    private final TicketsFeignClient ticketsFeignClient;

    @Override
    public List<TicketCustomerResponseModel> getBy(List<Integer> ticketsIds) {
        List<TicketResponseEntity> ticketsResponseEntity = new ArrayList<>();
        ticketsIds.forEach(ticketsId -> {
            TicketResponseEntity ticketResponseEntity = ticketsFeignClient.getTicketById(ticketsId);
            if (ticketResponseEntity != null) {
                ticketsResponseEntity.add(ticketResponseEntity);
            }
        });
        return ticketsResponseEntity.stream()
                .map(ticketResponseEntity -> new TicketCustomerResponseModel(
                        ticketResponseEntity.id(),
                        ticketResponseEntity.name(),
                        TicketTypeResponseModel.valueOf(ticketResponseEntity.type().toString()),
                        new TicketUnitPriceResponseModel(ticketResponseEntity.unitPrice().value(),
                                CurrencyResponseModel.valueOf(ticketResponseEntity.unitPrice().currency().toString()))
                )).collect(Collectors.toList());
    }
}
