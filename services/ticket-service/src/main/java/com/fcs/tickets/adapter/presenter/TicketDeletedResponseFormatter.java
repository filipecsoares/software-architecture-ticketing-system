package com.fcs.tickets.adapter.presenter;

import com.fcs.tickets.usecase.exception.BusinessException;
import com.fcs.tickets.usecase.presenter.TicketDeletedPresenter;
import org.springframework.stereotype.Component;

@Component
public class TicketDeletedResponseFormatter implements TicketDeletedPresenter {

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
