package com.fcs.admin.usecase.input;

import com.fcs.admin.usecase.model.TicketResponseModel;

import java.util.List;

public interface GetAllTicketsInputBoundary {

    List<TicketResponseModel> execute();
}
