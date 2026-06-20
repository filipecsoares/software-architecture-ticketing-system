package com.fcs.admin.usecase.input.impl;

import com.fcs.admin.usecase.gateway.EventGateway;
import com.fcs.admin.usecase.input.CreateEventInputBoundary;
import com.fcs.admin.usecase.model.EventCreateModel;
import com.fcs.admin.usecase.model.EventCreatedResponseModel;
import com.fcs.admin.usecase.presenter.EventCreatedPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateEventInteractor implements CreateEventInputBoundary {

    private final EventCreatedPresenter eventCreatedPresenter;
    private final EventGateway eventGateway;

    @Override
    public EventCreatedResponseModel execute(EventCreateModel toCreate) {
        try {
            return eventCreatedPresenter.prepareSuccessView(eventGateway.create(toCreate));
        } catch (Exception e) {
            eventCreatedPresenter.prepareFailView("Ocorreu um erro ao cadastrar o evento. Causa: " + e.getMessage());
        }
        return null;
    }
}
