package com.fcs.events.usecase.input.impl;

import com.fcs.events.entity.Event;
import com.fcs.events.entity.Session;
import com.fcs.events.usecase.gateway.EventGateway;
import com.fcs.events.usecase.gateway.RoomGateway;
import com.fcs.events.usecase.gateway.TicketGateway;
import com.fcs.events.usecase.input.CreateEventInputBoundary;
import com.fcs.events.usecase.model.EventCreatedResponseModel;
import com.fcs.events.usecase.model.RoomModel;
import com.fcs.events.usecase.model.TicketModel;
import com.fcs.events.usecase.presenter.EventCreatedPresenter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CreateEventInteractor implements CreateEventInputBoundary {

    private final RoomGateway roomGateway;
    private final TicketGateway ticketGateway;
    private final EventCreatedPresenter eventCreatedPresenter;
    private final EventGateway eventGateway;

    public CreateEventInteractor(final RoomGateway roomGateway, final TicketGateway ticketGateway, final EventCreatedPresenter eventCreatedPresenter, final EventGateway eventGateway) {
        this.roomGateway = roomGateway;
        this.ticketGateway = ticketGateway;
        this.eventCreatedPresenter = eventCreatedPresenter;
        this.eventGateway = eventGateway;
    }

    @Override
    public EventCreatedResponseModel execute(final Event toCreate) {
        if (!toCreate.isValid())
            eventCreatedPresenter.prepareFailView("Verifique que todas as informações do evento estão preenchidas.");
        boolean areValidSessionsDateRange = toCreate.areValidSessionsDateRange();
        if (!areValidSessionsDateRange)
            eventCreatedPresenter.prepareFailView("Verifique se todas as sessões estão dentro da vigência do evento.");
        for (Session session : toCreate.getSessions()) {
            RoomModel roomModel = roomGateway.findBy(session.getRoomId(), session.getStartDateTime());
            if (!roomModel.exists())
                eventCreatedPresenter.prepareFailView("A sala '" + session.getRoomId() + "' não existe.");
            if (!roomModel.isAvailable())
                eventCreatedPresenter.prepareFailView("A sala '" + roomModel.name() + "' não está disponível na data/hora da sessão.");
            boolean allocated = roomGateway.allocate(session.getRoomId(), session.getStartDateTime(), session.getEndDateTime());
            if (!allocated)
                eventCreatedPresenter.prepareFailView("Não foi possível alocar a sala '" + roomModel.name() + "' na sessão '" +  session.getName() + "' no intervalo de '" + session.getStartDateTime() + " " + session.getEndDateTime() + "'");
            Integer totalTicketsForSession = 0;
            for (Map.Entry<Integer, Integer> ticketEntry : session.getTicketTypeIdsByQtd().entrySet()) {
                TicketModel ticketModel = ticketGateway.findBy(ticketEntry.getKey());
                if (!ticketModel.exists())
                    eventCreatedPresenter.prepareFailView("O tipo de ingresso '" + ticketEntry.getKey() + "' não existe.");
                Integer ticketQuantity = ticketEntry.getValue();
                totalTicketsForSession += ticketQuantity;
            }
            Integer roomSeats = roomModel.totalSeats();
            if (!(roomSeats >= totalTicketsForSession)) {
                eventCreatedPresenter.prepareFailView("A sala '" + roomModel.name() + "' com '" + roomSeats + "' cadeiras"
                        + " não comporta o exigido de '" + totalTicketsForSession + "' para a sessão '" + session.getName() + "'");
            }
        }
        boolean eventAlreadyExists = eventGateway.exists(toCreate.getName());
        if (eventAlreadyExists) {
            eventCreatedPresenter.prepareFailView("Já existe um evento cadastrado com o nome '" + toCreate.getName() + "'");
        }
        Integer createdId = eventGateway.create(toCreate);
        toCreate.setId(createdId);
        return eventCreatedPresenter.prepareSuccessView(toCreate);
    }
}
