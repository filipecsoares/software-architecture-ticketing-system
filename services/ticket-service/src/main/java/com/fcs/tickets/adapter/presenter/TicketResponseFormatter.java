package com.fcs.tickets.adapter.presenter;

import com.fcs.tickets.usecase.exception.BusinessException;
import com.fcs.tickets.usecase.model.TicketResponseModel;
import com.fcs.tickets.usecase.presenter.TicketPresenter;
import org.springframework.stereotype.Component;

@Component
public class TicketResponseFormatter implements TicketPresenter {

    @Override
    public TicketResponseModel prepareSuccessView(TicketResponseModel ticketResponseModel) {
        return ticketResponseModel;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}