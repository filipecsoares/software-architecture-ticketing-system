package com.fcs.tickets.usecase.input.impl;

import com.fcs.tickets.usecase.exception.BusinessException;
import com.fcs.tickets.usecase.gateway.TicketGateway;
import com.fcs.tickets.usecase.presenter.TicketDeletedPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteTicketInteractorTest {

    @Mock
    private TicketDeletedPresenter ticketDeletedPresenter;

    @Mock
    private TicketGateway ticketGateway;

    private DeleteTicketInteractor interactor;

    @BeforeEach
    void setUp() {
        interactor = new DeleteTicketInteractor(ticketDeletedPresenter, ticketGateway);
    }

    @Test
    void shouldDeleteTicket_whenTicketExists() {
        // Given
        Integer ticketId = 1;
        doNothing().when(ticketGateway).delete(ticketId);

        // When
        interactor.execute(ticketId);

        // Then
        verify(ticketGateway).delete(ticketId);
        verify(ticketDeletedPresenter, never()).prepareFailView(any());
    }

    @Test
    void shouldThrowBusinessException_whenGatewayThrowsAnException() {
        // Given
        Integer ticketId = 99;
        String expectedMessage = "Ocorreu um erro interno ao excluir o ingresso.";
        doThrow(new RuntimeException("DB error")).when(ticketGateway).delete(ticketId);
        doThrow(new BusinessException(expectedMessage))
                .when(ticketDeletedPresenter).prepareFailView(expectedMessage);

        // When / Then
        BusinessException exception = assertThrows(BusinessException.class, () -> interactor.execute(ticketId));

        assertEquals(expectedMessage, exception.getMessage());
        verify(ticketDeletedPresenter).prepareFailView(expectedMessage);
    }
}
