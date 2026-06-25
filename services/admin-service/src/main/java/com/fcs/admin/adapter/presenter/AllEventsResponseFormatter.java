package com.fcs.admin.adapter.presenter;

import com.fcs.admin.usecase.model.EventResponseModel;
import com.fcs.admin.usecase.presenter.AllEventsPresenter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllEventsResponseFormatter implements AllEventsPresenter {

    @Override
    public List<EventResponseModel> prepareSuccessView(List<EventResponseModel> allEvents) {
        return allEvents;
    }

}
