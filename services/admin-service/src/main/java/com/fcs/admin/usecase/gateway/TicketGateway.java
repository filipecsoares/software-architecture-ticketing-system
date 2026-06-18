package com.fcs.admin.usecase.gateway;

import com.fcs.admin.usecase.model.CreateTicketModel;
import com.fcs.admin.usecase.model.TicketCreatedResponseModel;
import com.fcs.admin.usecase.model.TicketResponseModel;

import java.util.List;

public interface TicketGateway {

    TicketCreatedResponseModel create(CreateTicketModel toCreate);

    void delete(Integer ticketId);

    List<TicketResponseModel> getAll();
}
