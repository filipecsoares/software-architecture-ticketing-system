package com.fcs.customers.adapter.presenter;

import com.fcs.customers.usecase.exception.BusinessException;
import com.fcs.customers.usecase.model.EventCustomerResponseModel;
import com.fcs.customers.usecase.presenter.EventsToCustomerPresenter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventsToCustomerResponseFormatter implements EventsToCustomerPresenter {

    @Override
    public List<EventCustomerResponseModel> prepareSuccessView(List<EventCustomerResponseModel> eventsCustomerResponseModel) {
        return eventsCustomerResponseModel;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
