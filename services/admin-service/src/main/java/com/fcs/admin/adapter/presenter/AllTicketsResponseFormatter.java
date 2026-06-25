package com.fcs.admin.adapter.presenter;

import com.fcs.admin.usecase.model.TicketResponseModel;
import com.fcs.admin.usecase.presenter.AllTicketsPresenter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllTicketsResponseFormatter implements AllTicketsPresenter {

    @Override
    public List<TicketResponseModel> prepareSuccessView(List<TicketResponseModel> allTickets) {
        return allTickets;
    }

}
