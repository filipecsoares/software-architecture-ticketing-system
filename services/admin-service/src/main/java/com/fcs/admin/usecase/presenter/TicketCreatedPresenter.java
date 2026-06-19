package com.fcs.admin.usecase.presenter;

import com.fcs.admin.usecase.model.TicketCreatedResponseModel;

public interface TicketCreatedPresenter extends BasePresenter {

    TicketCreatedResponseModel prepareSuccessView(TicketCreatedResponseModel ticketCreatedResponseModel);
}
