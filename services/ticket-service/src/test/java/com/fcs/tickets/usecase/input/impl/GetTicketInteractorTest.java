package com.fcs.tickets.usecase.input.impl;

import com.fcs.tickets.usecase.exception.BusinessException;
import com.fcs.tickets.usecase.gateway.TicketGateway;
import com.fcs.tickets.usecase.model.TicketResponseModel;
import com.fcs.tickets.usecase.presenter.TicketPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTicketInteractorTest {

    @Mock
    private TicketPresenter ticketPresenter;

    @Mock
    private TicketGateway ticketGateway;

    private GetTicketInteractor interactor;

    @BeforeEach
    void setUp() {
        interactor = new GetTicketInteractor(ticketPresenter, ticketGateway);
    }

    @Test
    void shouldReturnTicket_whenTicketExists() {
        // Given
        Integer ticketId = 1;
        TicketResponseModel gatewayResponse = new TicketResponseModel(1, "Show A", null, null);
        TicketResponseModel presenterResponse = new TicketResponseModel(1, "Show A", null, null);
        when(ticketGateway.getById(ticketId)).thenReturn(gatewayResponse);
        when(ticketPresenter.prepareSuccessView(gatewayResponse)).thenReturn(presenterResponse);

        // When
        TicketResponseModel result = interactor.execute(ticketId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.id());
        assertEquals("Show A", result.name());
        verify(ticketPresenter).prepareSuccessView(gatewayResponse);
        verify(ticketPresenter, never()).prepareFailView(any());
    }

    @Test
    void shouldThrowBusinessException_whenGatewayThrowsAnException() {
        // Given
        Integer ticketId = 99;
        String expectedMessage = "Ocorreu um erro interno ao obter os dados do ingresso.";
        when(ticketGateway.getById(ticketId)).thenThrow(new RuntimeException("DB error"));
        doThrow(new BusinessException(expectedMessage))
                .when(ticketPresenter).prepareFailView(expectedMessage);

        // When / Then
        BusinessException exception = assertThrows(BusinessException.class, () -> interactor.execute(ticketId));

        assertEquals(expectedMessage, exception.getMessage());
        verify(ticketPresenter).prepareFailView(expectedMessage);
        verify(ticketPresenter, never()).prepareSuccessView(any());
    }
}
