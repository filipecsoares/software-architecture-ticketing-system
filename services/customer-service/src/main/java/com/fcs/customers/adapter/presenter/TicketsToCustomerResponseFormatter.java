package com.fcs.customers.adapter.presenter;

import com.fcs.customers.usecase.exception.BusinessException;
import com.fcs.customers.usecase.model.TicketCustomerResponseModel;
import com.fcs.customers.usecase.presenter.TicketsToCustomerPresenter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketsToCustomerResponseFormatter implements TicketsToCustomerPresenter {

    @Override
    public List<TicketCustomerResponseModel> prepareSuccessView(List<TicketCustomerResponseModel> ticketCustomerResponseModels) {
        return ticketCustomerResponseModels;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
