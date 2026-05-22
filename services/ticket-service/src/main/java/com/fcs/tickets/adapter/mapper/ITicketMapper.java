package com.fcs.tickets.adapter.mapper;

import com.fcs.tickets.adapter.entrypoint.dto.CreateTicketDto;
import com.fcs.tickets.entity.Ticket;
import com.fcs.tickets.entity.vo.Currency;
import com.fcs.tickets.entity.vo.TicketType;
import com.fcs.tickets.entity.vo.TicketUnitPrice;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ITicketMapper {

    default Ticket createTicketDtoToTicket(CreateTicketDto requestDto) {
        Ticket ticket = new Ticket();
        ticket.setName(requestDto.name());
        ticket.setType(TicketType.valueOf(requestDto.ticketType().name()));
        ticket.setUnitPrice(new TicketUnitPrice(requestDto.ticketUnitPrice().value(), Currency.valueOf(requestDto.ticketUnitPrice().currency())));
        return ticket;
    }
}
