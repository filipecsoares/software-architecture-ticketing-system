package com.fcs.admin.adapter.presenter;

import com.fcs.admin.usecase.model.TicketResponseModel;
import com.fcs.admin.usecase.presenter.TicketPresenter;
import org.springframework.stereotype.Component;

@Component
public class TicketResponseFormatter implements TicketPresenter {

    @Override
    public TicketResponseModel prepareSuccessView(TicketResponseModel ticketResponseModel) {
        return ticketResponseModel;
    }
}
