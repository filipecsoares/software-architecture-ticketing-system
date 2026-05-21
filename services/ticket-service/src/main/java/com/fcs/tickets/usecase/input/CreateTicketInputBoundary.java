package com.fcs.tickets.usecase.input;

import com.fcs.tickets.entity.Ticket;
import com.fcs.tickets.usecase.model.TicketCreatedResponseModel;

public interface CreateTicketInputBoundary {

    TicketCreatedResponseModel execute(final Ticket toCreate);
}
