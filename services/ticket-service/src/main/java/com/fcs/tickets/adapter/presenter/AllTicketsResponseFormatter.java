package com.fcs.tickets.adapter.presenter;

import com.fcs.tickets.usecase.exception.BusinessException;
import com.fcs.tickets.usecase.model.TicketResponseModel;
import com.fcs.tickets.usecase.presenter.AllTicketsPresenter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllTicketsResponseFormatter implements AllTicketsPresenter {

    @Override
    public List<TicketResponseModel> prepareSuccessView(List<TicketResponseModel> allTickets) {
        return allTickets;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
