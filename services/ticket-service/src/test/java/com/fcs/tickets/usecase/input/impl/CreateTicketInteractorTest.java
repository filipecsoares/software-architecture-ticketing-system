package com.fcs.tickets.usecase.input.impl;

import com.fcs.tickets.entity.Ticket;
import com.fcs.tickets.entity.vo.Currency;
import com.fcs.tickets.entity.vo.TicketType;
import com.fcs.tickets.entity.vo.TicketUnitPrice;
import com.fcs.tickets.usecase.exception.BusinessException;
import com.fcs.tickets.usecase.gateway.TicketGateway;
import com.fcs.tickets.usecase.model.TicketCreatedResponseModel;
import com.fcs.tickets.usecase.presenter.TicketCreatedPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTicketInteractorTest {

    @Mock
    private TicketGateway ticketGateway;

    @Mock
    private TicketCreatedPresenter ticketCreatedPresenter;

    private CreateTicketInteractor interactor;

    @BeforeEach
    void setUp() {
        interactor = new CreateTicketInteractor(ticketGateway, ticketCreatedPresenter);
    }

    @Test
    void shouldCreateTicketAndReturnSuccessView_whenTicketIsValidAndDoesNotExist() {
        // Given
        Ticket ticket = new Ticket(null, "Show A", TicketType.INTEIRA, new TicketUnitPrice(50.0, Currency.REAL));
        when(ticketGateway.exists("Show A")).thenReturn(false);
        when(ticketGateway.create(ticket)).thenReturn(1);
        TicketCreatedResponseModel expectedResponse = new TicketCreatedResponseModel(1);
        when(ticketCreatedPresenter.prepareSuccessView(ticket)).thenReturn(expectedResponse);

        // When
        TicketCreatedResponseModel result = interactor.execute(ticket);

        // Then
        assertNotNull(result);
        assertEquals(1, result.createdId());
        verify(ticketGateway).create(ticket);
        verify(ticketCreatedPresenter).prepareSuccessView(ticket);
    }

    @Test
    void shouldThrowBusinessException_whenTicketIsInvalid() {
        // Given
        Ticket ticket = new Ticket(null, null, null, null);
        String expectedMessage = "Verifique que todas as informações do ingresso estão preenchidas.";
        doThrow(new BusinessException(expectedMessage))
                .when(ticketCreatedPresenter).prepareFailView(expectedMessage);

        // When / Then
        BusinessException exception = assertThrows(BusinessException.class, () -> interactor.execute(ticket));

        assertEquals(expectedMessage, exception.getMessage());
        verify(ticketCreatedPresenter).prepareFailView(expectedMessage);
        verify(ticketGateway, never()).create(any());
    }

    @Test
    void shouldThrowBusinessException_whenTicketAlreadyExists() {
        // Given
        Ticket ticket = new Ticket(null, "Show A", TicketType.INTEIRA, new TicketUnitPrice(50.0, Currency.REAL));
        String expectedMessage = "Já existe um ingresso cadastrado com o nome 'Show A'";
        when(ticketGateway.exists("Show A")).thenReturn(true);
        doThrow(new BusinessException(expectedMessage))
                .when(ticketCreatedPresenter).prepareFailView(expectedMessage);

        // When / Then
        BusinessException exception = assertThrows(BusinessException.class, () -> interactor.execute(ticket));

        assertEquals(expectedMessage, exception.getMessage());
        verify(ticketCreatedPresenter).prepareFailView(expectedMessage);
        verify(ticketGateway, never()).create(any());
    }
}
