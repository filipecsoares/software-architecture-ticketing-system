package com.fcs.admin.usecase.presenter;

import com.fcs.admin.usecase.model.TicketResponseModel;

import java.util.List;

public interface AllTicketsPresenter extends BasePresenter {

    List<TicketResponseModel> prepareSuccessView(List<TicketResponseModel> allTickets);
}
