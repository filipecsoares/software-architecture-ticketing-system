package com.fcs.admin.usecase.input.impl;

import com.fcs.admin.usecase.gateway.EventGateway;
import com.fcs.admin.usecase.input.DeleteEventInputBoundary;
import com.fcs.admin.usecase.presenter.EventDeletedPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteEventInteractor implements DeleteEventInputBoundary {

    private final EventDeletedPresenter eventDeletedPresenter;
    private final EventGateway eventGateway;

    @Override
    public void execute(Integer eventId) {
        try {
            eventGateway.delete(eventId);
        } catch (Exception e) {
            eventDeletedPresenter.prepareFailView("Ocorreu um erro interno ao excluir o evento. Causa: " + e.getMessage());
        }
    }
}
