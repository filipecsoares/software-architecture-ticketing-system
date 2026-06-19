package com.fcs.admin.usecase.presenter;

import com.fcs.admin.usecase.model.TicketResponseModel;

public interface TicketPresenter extends BasePresenter {

    TicketResponseModel prepareSuccessView(TicketResponseModel ticketResponseModel);
}
