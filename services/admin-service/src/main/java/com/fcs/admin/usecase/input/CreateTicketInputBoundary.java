package com.fcs.admin.usecase.input;

import com.fcs.admin.usecase.model.CreateTicketModel;
import com.fcs.admin.usecase.model.TicketCreatedResponseModel;

public interface CreateTicketInputBoundary {

    TicketCreatedResponseModel execute(CreateTicketModel toCreate);
}
