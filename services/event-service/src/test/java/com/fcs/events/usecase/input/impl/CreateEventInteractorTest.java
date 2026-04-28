package com.fcs.events.usecase.input.impl;

import com.fcs.events.entity.Event;
import com.fcs.events.entity.Session;
import com.fcs.events.entity.vo.Validity;
import com.fcs.events.usecase.exception.BusinessException;
import com.fcs.events.usecase.gateway.EventGateway;
import com.fcs.events.usecase.gateway.RoomGateway;
import com.fcs.events.usecase.gateway.TicketGateway;
import com.fcs.events.usecase.model.EventCreatedResponseModel;
import com.fcs.events.usecase.model.RoomModel;
import com.fcs.events.usecase.model.TicketModel;
import com.fcs.events.usecase.presenter.EventCreatedPresenter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateEventInteractorTest {

    @Mock
    private RoomGateway roomGateway;
    @Mock
    private TicketGateway ticketGateway;
    @Mock
    private EventCreatedPresenter eventCreatedPresenter;
    @Mock
    private EventGateway eventGateway;

    @InjectMocks
    private CreateEventInteractor createEventInteractor;

    @Test
    public void given_Event_whenEventIsNotValid_should_return_error() {

        // Given

        LocalDateTime sessionOneStartLocalDateTime = LocalDateTime.of(2024, 2, 3, 0, 0, 0);
        LocalDateTime sessionOneEndLocalDateTime = LocalDateTime.of(2024, 2, 3, 2, 0, 0);
        LocalDateTime sessionTwoStartLocalDateTime = LocalDateTime.of(2024, 2, 7, 0, 0, 0);
        LocalDateTime sessionTwoEndLocalDateTime = LocalDateTime.of(2024, 2, 7, 2, 0, 0);

        /* Sessoes sem nome */
        Session sessionOne = createSession("", sessionOneStartLocalDateTime, sessionOneEndLocalDateTime,1, Map.of(1, 10));
        Session sessionTwo = createSession("", sessionTwoStartLocalDateTime, sessionTwoEndLocalDateTime, 2, Map.of(2, 5));

        Event toCreate = createEvent(List.of(sessionOne, sessionTwo), new Validity(
                LocalDateTime.of(2024, 1, 1, 0, 0, 0),
                LocalDateTime.of(2024, 3, 1, 0, 0, 0))
        );

        doThrow(new BusinessException("Verifique que todas as informações do evento estão preenchidas."))
                .when(eventCreatedPresenter).prepareFailView("Verifique que todas as informações do evento estão preenchidas.");

        // When

        assertThrows(BusinessException.class, () -> {
            createEventInteractor.execute(toCreate);
        });

        // Then

        verify(eventCreatedPresenter, times(1)).prepareFailView("Verifique que todas as informações do evento estão preenchidas.");
    }

    @Test
    public void given_Event_whenSessionsAndRoomsAreValid_should_return_success() {

        // Given

        LocalDateTime sessionOneStartLocalDateTime = LocalDateTime.of(2024, 2, 3, 0, 0, 0);
        LocalDateTime sessionOneEndLocalDateTime = LocalDateTime.of(2024, 2, 3, 2, 0, 0);
        LocalDateTime sessionTwoStartLocalDateTime = LocalDateTime.of(2024, 2, 7, 0, 0, 0);
        LocalDateTime sessionTwoEndLocalDateTime = LocalDateTime.of(2024, 2, 7, 2, 0, 0);

        Session sessionOne = createSession("Sessao 1", sessionOneStartLocalDateTime, sessionOneEndLocalDateTime, 1, Map.of(1, 10));
        Session sessionTwo = createSession("Sessao 2", sessionTwoStartLocalDateTime, sessionTwoEndLocalDateTime,2, Map.of(2, 5));

        Event toCreate = createEvent(List.of(sessionOne, sessionTwo), new Validity(
                LocalDateTime.of(2024, 1, 1, 0, 0, 0),
                LocalDateTime.of(2024, 3, 1, 0, 0, 0))
        );

        RoomModel roomModelOne = createRoomModel("Sala Grande", 10, true, true);
        given(roomGateway.findBy(toCreate.getSessions().get(0).getRoomId(), toCreate.getSessions().get(0).getStartDateTime()))
                .willReturn(roomModelOne);
        RoomModel roomModelTwo = createRoomModel("Sala Pequena", 5, true, true);
        given(roomGateway.findBy(toCreate.getSessions().get(1).getRoomId(), toCreate.getSessions().get(1).getStartDateTime()))
                .willReturn(roomModelTwo);

        given(roomGateway.allocate(toCreate.getSessions().get(0).getRoomId(), toCreate.getSessions().get(0).getStartDateTime(), toCreate.getSessions().get(0).getEndDateTime()))
                .willReturn(true);
        given(roomGateway.allocate(toCreate.getSessions().get(1).getRoomId(), toCreate.getSessions().get(1).getStartDateTime(), toCreate.getSessions().get(1).getEndDateTime()))
                .willReturn(true);

        TicketModel ticketModelOne = new TicketModel("INTEIRA", true);
        given(ticketGateway.findBy(1))
                .willReturn(ticketModelOne);

        TicketModel ticketModelTwo = new TicketModel("MEIA", true);
        given(ticketGateway.findBy(2))
                .willReturn(ticketModelTwo);

        given(eventCreatedPresenter.prepareSuccessView(any()))
                .willReturn(new EventCreatedResponseModel(1));

        given(eventGateway.create(toCreate))
                .willReturn(1);

        // When

        EventCreatedResponseModel eventCreatedResponseModel = createEventInteractor.execute(toCreate);

        // Then

        assertEquals(1, eventCreatedResponseModel.createdId());
        verify(eventCreatedPresenter, never()).prepareFailView(any());
    }

    @Test
    public void given_Event_whenSessionsHaveInvalidDate_should_return_error() {

        // Given

        LocalDateTime sessionOneStartLocalDateTime = LocalDateTime.of(2024, 2, 3, 0, 0, 0);
        LocalDateTime sessionOneEndLocalDateTime = LocalDateTime.of(2024, 2, 3, 2, 0, 0);
        LocalDateTime sessionTwoStartLocalDateTime = LocalDateTime.of(2024, 2, 7, 0, 0, 0);
        LocalDateTime sessionTwoEndLocalDateTime = LocalDateTime.of(2024, 2, 7, 2, 0, 0);


        Session sessionOne = createSession("Sessao 1", sessionOneStartLocalDateTime, sessionOneEndLocalDateTime, 1, Map.of(1, 10));
        Session sessionTwo = createSession("Sessao 2", sessionTwoStartLocalDateTime, sessionTwoEndLocalDateTime, 2, Map.of(2, 5));

        Event toCreate = createEvent(List.of(sessionOne, sessionTwo), new Validity(
                LocalDateTime.of(2024, 3, 1, 0, 0, 0),
                LocalDateTime.of(2024, 4, 1, 0, 0, 0))
        );

        doThrow(new BusinessException("Verifique se todas as sessões estão dentro da vigência do evento."))
                .when(eventCreatedPresenter).prepareFailView("Verifique se todas as sessões estão dentro da vigência do evento.");

        // When

        assertThrows(BusinessException.class, () -> {
            createEventInteractor.execute(toCreate);
        });

        // Then

        verify(eventCreatedPresenter, times(1)).prepareFailView("Verifique se todas as sessões estão dentro da vigência do evento.");
    }

    @Test
    public void given_Event_whenRoomDoesNotExist_should_return_error() {

        // Given

        LocalDateTime sessionOneStartLocalDateTime = LocalDateTime.of(2024, 2, 3, 0, 0, 0);
        LocalDateTime sessionOneEndLocalDateTime = LocalDateTime.of(2024, 2, 3, 2, 0, 0);

        Session sessionOne = createSession("Sessao 1", sessionOneStartLocalDateTime, sessionOneEndLocalDateTime,1, Map.of(1, 10));

        Event toCreate = createEvent(List.of(sessionOne), new Validity(
                LocalDateTime.of(2024, 1, 1, 0, 0, 0),
                LocalDateTime.of(2024, 3, 1, 0, 0, 0))
        );

        RoomModel roomModelOne = createRoomModel(null, null, false, false);
        given(roomGateway.findBy(toCreate.getSessions().get(0).getRoomId(), toCreate.getSessions().get(0).getStartDateTime()))
                .willReturn(roomModelOne);

        doThrow(new BusinessException("A sala '1' não existe."))
                .when(eventCreatedPresenter).prepareFailView("A sala '1' não existe.");

        // When
        assertThrows(BusinessException.class, () -> {
            createEventInteractor.execute(toCreate);
        });

        // Then

        verify(eventCreatedPresenter, times(1)).prepareFailView("A sala '1' não existe.");
    }

    @Test
    public void given_Event_whenRoomIsNotAvailable_should_return_error() {

        // Given

        LocalDateTime sessionOneStartLocalDateTime = LocalDateTime.of(2024, 2, 3, 0, 0, 0);
        LocalDateTime sessionOneEndLocalDateTime = LocalDateTime.of(2024, 2, 3, 2, 0, 0);

        Session sessionOne = createSession("Sessao 1", sessionOneStartLocalDateTime, sessionOneEndLocalDateTime,1, Map.of(1, 10));

        Event toCreate = createEvent(List.of(sessionOne), new Validity(
                LocalDateTime.of(2024, 1, 1, 0, 0, 0),
                LocalDateTime.of(2024, 3, 1, 0, 0, 0))
        );

        RoomModel roomModelOne = createRoomModel("Sala Grande", 10, true, false);
        given(roomGateway.findBy(toCreate.getSessions().get(0).getRoomId(), toCreate.getSessions().get(0).getStartDateTime()))
                .willReturn(roomModelOne);

        doThrow(new BusinessException("A sala 'Sala Grande' não está disponível na data/hora da sessão."))
                .when(eventCreatedPresenter).prepareFailView("A sala 'Sala Grande' não está disponível na data/hora da sessão.");

        // When
        assertThrows(BusinessException.class, () -> {
            createEventInteractor.execute(toCreate);
        });

        // Then

        verify(eventCreatedPresenter, times(1)).prepareFailView("A sala 'Sala Grande' não está disponível na data/hora da sessão.");
    }

    @Test
    public void given_Event_whenRoomCouldNotBeAllocated_should_return_error() {

        // Given

        LocalDateTime sessionOneStartLocalDateTime = LocalDateTime.of(2024, 2, 3, 0, 0, 0);
        LocalDateTime sessionOneEndLocalDateTime = LocalDateTime.of(2024, 2, 3, 2, 0, 0);

        Session sessionOne = createSession("Sessao 1", sessionOneStartLocalDateTime, sessionOneEndLocalDateTime,1, Map.of(1, 10));

        Event toCreate = createEvent(List.of(sessionOne), new Validity(
                LocalDateTime.of(2024, 1, 1, 0, 0, 0),
                LocalDateTime.of(2024, 3, 1, 0, 0, 0))
        );


        RoomModel roomModel = createRoomModel("Sala Grande", 10, true, true);
        given(roomGateway.findBy(toCreate.getSessions().getFirst().getRoomId(), toCreate.getSessions().getFirst().getStartDateTime()))
                .willReturn(roomModel);

        given(roomGateway.allocate(toCreate.getSessions().getFirst().getRoomId(), toCreate.getSessions().getFirst().getStartDateTime(), toCreate.getSessions().getFirst().getEndDateTime()))
                .willReturn(false);

        doThrow(new BusinessException("Não foi possível alocar a sala '" + roomModel.name() + "' na sessão '" +  sessionOne.getName() + "' no intervalo de '" + sessionOne.getStartDateTime() + " " + sessionOne.getEndDateTime() + "'"))
                .when(eventCreatedPresenter).prepareFailView("Não foi possível alocar a sala '" + roomModel.name() + "' na sessão '" +  sessionOne.getName() + "' no intervalo de '" + sessionOne.getStartDateTime() + " " + sessionOne.getEndDateTime() + "'");

        // When
        assertThrows(BusinessException.class, () -> {
            createEventInteractor.execute(toCreate);
        });

        // Then

        verify(eventCreatedPresenter, times(1)).prepareFailView("Não foi possível alocar a sala '" + roomModel.name() + "' na sessão '" +  sessionOne.getName() + "' no intervalo de '" + sessionOne.getStartDateTime() + " " + sessionOne.getEndDateTime() + "'");
    }

    @Test
    public void given_Event_whenTicketTypeDoesNotExist_should_return_error() {

        // Given

        LocalDateTime sessionOneStartLocalDateTime = LocalDateTime.of(2024, 2, 3, 0, 0, 0);
        LocalDateTime sessionOneEndLocalDateTime = LocalDateTime.of(2024, 2, 3, 2, 0, 0);

        Session sessionOne = createSession("Sessao 1", sessionOneStartLocalDateTime, sessionOneEndLocalDateTime, 1, Map.of(1, 10));

        Event toCreate = createEvent(List.of(sessionOne), new Validity(
                LocalDateTime.of(2024, 1, 1, 0, 0, 0),
                LocalDateTime.of(2024, 3, 1, 0, 0, 0))
        );

        RoomModel roomModelOne = createRoomModel("Sala Grande", 10, true, true);
        given(roomGateway.findBy(toCreate.getSessions().getFirst().getRoomId(), toCreate.getSessions().getFirst().getStartDateTime()))
                .willReturn(roomModelOne);

        given(roomGateway.allocate(toCreate.getSessions().getFirst().getRoomId(), toCreate.getSessions().getFirst().getStartDateTime(), toCreate.getSessions().getFirst().getEndDateTime()))
                .willReturn(true);

        TicketModel ticketModelOne = new TicketModel(null, false);
        given(ticketGateway.findBy(1))
                .willReturn(ticketModelOne);

        doThrow(new BusinessException("O tipo de ingresso '1' não existe."))
                .when(eventCreatedPresenter).prepareFailView("O tipo de ingresso '1' não existe.");

        // When
        assertThrows(BusinessException.class, () -> {
            createEventInteractor.execute(toCreate);
        });

        // Then

        verify(eventCreatedPresenter, times(1)).prepareFailView("O tipo de ingresso '1' não existe.");
    }

    @Test
    public void given_Event_whenRoomHasLessTablesThanRequiredBySession_should_return_error() {

        // Given

        LocalDateTime sessionOneStartLocalDateTime = LocalDateTime.of(2024, 2, 3, 0, 0, 0);
        LocalDateTime sessionOneEndLocalDateTime = LocalDateTime.of(2024, 2, 3, 2, 0, 0);

        Session sessionOne = createSession("Sessao 1", sessionOneStartLocalDateTime, sessionOneEndLocalDateTime, 1, Map.of(1, 10));

        Event toCreate = createEvent(List.of(sessionOne), new Validity(
                LocalDateTime.of(2024, 1, 1, 0, 0, 0),
                LocalDateTime.of(2024, 3, 1, 0, 0, 0))
        );

        RoomModel roomModelOne = createRoomModel("Sala Grande", 5, true, true);
        given(roomGateway.findBy(toCreate.getSessions().getFirst().getRoomId(), toCreate.getSessions().getFirst().getStartDateTime()))
                .willReturn(roomModelOne);

        given(roomGateway.allocate(toCreate.getSessions().getFirst().getRoomId(), toCreate.getSessions().getFirst().getStartDateTime(), toCreate.getSessions().getFirst().getEndDateTime()))
                .willReturn(true);

        TicketModel ticketModelOne = new TicketModel("INTEIRA", true);
        given(ticketGateway.findBy(1))
                .willReturn(ticketModelOne);

        doThrow(new BusinessException("A sala 'Sala Grande' com '5' cadeiras não comporta o exigido de '10' para a sessão 'Sessao 1'"))
                .when(eventCreatedPresenter).prepareFailView("A sala 'Sala Grande' com '5' cadeiras não comporta o exigido de '10' para a sessão 'Sessao 1'");

        // When
        assertThrows(BusinessException.class, () -> {
            createEventInteractor.execute(toCreate);
        });

        // Then

        verify(eventCreatedPresenter, times(1)).prepareFailView("A sala 'Sala Grande' com '5' cadeiras não comporta o exigido de '10' para a sessão 'Sessao 1'");
    }

    @Test
    public void given_Event_whenValidButAlreadyExists_should_return_error() {

        // Given

        LocalDateTime sessionOneStartLocalDateTime = LocalDateTime.of(2024, 2, 3, 0, 0, 0);
        LocalDateTime sessionOneEndLocalDateTime = LocalDateTime.of(2024, 2, 3, 2, 0, 0);
        LocalDateTime sessionTwoStartLocalDateTime = LocalDateTime.of(2024, 2, 7, 0, 0, 0);
        LocalDateTime sessionTwoEndLocalDateTime = LocalDateTime.of(2024, 2, 7, 2, 0, 0);

        Session sessionOne = createSession("Sessao 1", sessionOneStartLocalDateTime, sessionOneEndLocalDateTime, 1, Map.of(1, 10));
        Session sessionTwo = createSession("Sessao 2", sessionTwoStartLocalDateTime, sessionTwoEndLocalDateTime,2, Map.of(2, 5));

        Event toCreate = createEvent(List.of(sessionOne, sessionTwo), new Validity(
                LocalDateTime.of(2024, 1, 1, 0, 0, 0),
                LocalDateTime.of(2024, 3, 1, 0, 0, 0))
        );

        RoomModel roomModelOne = createRoomModel("Sala Grande", 10, true, true);
        given(roomGateway.findBy(toCreate.getSessions().getFirst().getRoomId(), toCreate.getSessions().getFirst().getStartDateTime()))
                .willReturn(roomModelOne);
        RoomModel roomModelTwo = createRoomModel("Sala Pequena", 5, true, true);
        given(roomGateway.findBy(toCreate.getSessions().get(1).getRoomId(), toCreate.getSessions().get(1).getStartDateTime()))
                .willReturn(roomModelTwo);

        given(roomGateway.allocate(toCreate.getSessions().getFirst().getRoomId(), toCreate.getSessions().getFirst().getStartDateTime(), toCreate.getSessions().getFirst().getEndDateTime()))
                .willReturn(true);
        given(roomGateway.allocate(toCreate.getSessions().get(1).getRoomId(), toCreate.getSessions().get(1).getStartDateTime(), toCreate.getSessions().get(1).getEndDateTime()))
                .willReturn(true);

        TicketModel ticketModelOne = new TicketModel("INTEIRA", true);
        given(ticketGateway.findBy(1))
                .willReturn(ticketModelOne);

        TicketModel ticketModelTwo = new TicketModel("MEIA", true);
        given(ticketGateway.findBy(2))
                .willReturn(ticketModelTwo);

        given(eventGateway.exists("Evento"))
                .willReturn(true);

        doThrow(new BusinessException("Já existe um evento cadastrado com o nome 'Evento'"))
                .when(eventCreatedPresenter).prepareFailView("Já existe um evento cadastrado com o nome 'Evento'");

        // When
        assertThrows(BusinessException.class, () -> {
            createEventInteractor.execute(toCreate);
        });

        // Then

        verify(eventCreatedPresenter, times(1)).prepareFailView("Já existe um evento cadastrado com o nome 'Evento'");
    }

    private Session createSession(String name, LocalDateTime startDateTime, LocalDateTime endDateTime, Integer roomId, Map<Integer, Integer> ticketTypeIdsByQtd) {
        return new Session(name, startDateTime, endDateTime, roomId, ticketTypeIdsByQtd);
    }

    private Event createEvent(List<Session> sessions, Validity validity) {
        return new Event("Evento", validity, sessions);
    }

    private RoomModel createRoomModel(String name, Integer totalSeats, boolean exists, boolean isAvailable) {
        return new RoomModel(name, totalSeats, exists, isAvailable);
    }
}