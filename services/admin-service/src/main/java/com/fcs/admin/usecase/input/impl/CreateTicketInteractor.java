package com.fcs.admin.usecase.input.impl;

import com.fcs.admin.usecase.exception.BusinessException;
import com.fcs.admin.usecase.gateway.TicketGateway;
import com.fcs.admin.usecase.input.CreateTicketInputBoundary;
import com.fcs.admin.usecase.model.CreateTicketModel;
import com.fcs.admin.usecase.model.TicketCreatedResponseModel;
import com.fcs.admin.usecase.presenter.TicketCreatedPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTicketInteractor implements CreateTicketInputBoundary {

    private final TicketGateway ticketGateway;
    private final TicketCreatedPresenter ticketCreatedPresenter;

    @Override
    public TicketCreatedResponseModel execute(CreateTicketModel createTicketModel) {
        try {
            TicketCreatedResponseModel ticketCreatedResponseModel = ticketGateway.create(createTicketModel);
            if (ticketCreatedResponseModel != null && ticketCreatedResponseModel.createdId() != null) {
                return ticketCreatedPresenter.prepareSuccessView(ticketCreatedResponseModel);
            }
            throw new BusinessException("Ocorreu um erro ao realizar o cadastro do ingresso.");
        } catch (Exception e) {
            ticketCreatedPresenter.prepareFailView("Ocorreu um erro ao cadastrar o ingresso. Causa: " + e.getMessage());
        }
        return null;
    }
}
