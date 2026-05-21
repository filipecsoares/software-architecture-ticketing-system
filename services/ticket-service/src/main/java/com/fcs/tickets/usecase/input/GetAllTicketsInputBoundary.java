package com.fcs.tickets.usecase.input;

import com.fcs.tickets.usecase.model.TicketResponseModel;

import java.util.List;

public interface GetAllTicketsInputBoundary {

    List<TicketResponseModel> execute();
}
