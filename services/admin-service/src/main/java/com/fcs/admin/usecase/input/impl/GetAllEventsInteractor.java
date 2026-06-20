package com.fcs.admin.usecase.input.impl;

import com.fcs.admin.usecase.gateway.EventGateway;
import com.fcs.admin.usecase.input.GetAllEventsInputBoundary;
import com.fcs.admin.usecase.model.EventResponseModel;
import com.fcs.admin.usecase.presenter.AllEventsPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllEventsInteractor implements GetAllEventsInputBoundary {

    private final AllEventsPresenter allEventsPresenter;
    private final EventGateway eventGateway;

    @Override
    public List<EventResponseModel> execute() {
        List<EventResponseModel> allEvents = new ArrayList<>();
        try {
            allEvents = eventGateway.getAll();
        } catch (Exception e) {
            allEventsPresenter.prepareFailView("Ocorreu um erro interno ao obter todos os eventos cadastrados. Causa: " + e.getMessage());
        }
        return allEventsPresenter.prepareSuccessView(allEvents);
    }
}
