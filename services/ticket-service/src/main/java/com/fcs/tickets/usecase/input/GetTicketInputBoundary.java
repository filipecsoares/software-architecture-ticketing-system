package com.fcs.tickets.usecase.input;

import com.fcs.tickets.usecase.model.TicketResponseModel;

public interface GetTicketInputBoundary {

    TicketResponseModel execute(final Integer ticketId);
}
