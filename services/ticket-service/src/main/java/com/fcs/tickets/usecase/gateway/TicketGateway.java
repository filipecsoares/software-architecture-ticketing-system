package com.fcs.tickets.usecase.gateway;

import com.fcs.tickets.entity.Ticket;
import com.fcs.tickets.usecase.model.TicketResponseModel;

import java.util.List;

public interface TicketGateway {

    Integer create(final Ticket toCreate);

    void delete(final Integer ticketId);

    List<TicketResponseModel> getAll();

    TicketResponseModel getById(final Integer ticketId);

    boolean exists(final String name);
}
