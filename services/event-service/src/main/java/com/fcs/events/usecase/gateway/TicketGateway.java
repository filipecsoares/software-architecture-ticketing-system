package com.fcs.events.usecase.gateway;

import com.fcs.events.usecase.model.TicketModel;

public interface TicketGateway {
    TicketModel findBy(final Integer id);
}
