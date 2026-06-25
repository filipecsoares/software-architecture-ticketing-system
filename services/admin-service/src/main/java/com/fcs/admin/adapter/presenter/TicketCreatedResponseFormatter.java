package com.fcs.admin.adapter.presenter;

import com.fcs.admin.usecase.model.TicketCreatedResponseModel;
import com.fcs.admin.usecase.presenter.TicketCreatedPresenter;
import org.springframework.stereotype.Component;

@Component
public class TicketCreatedResponseFormatter implements TicketCreatedPresenter {

    @Override
    public TicketCreatedResponseModel prepareSuccessView(TicketCreatedResponseModel ticketCreatedResponseModel) {
        return ticketCreatedResponseModel;
    }
}
