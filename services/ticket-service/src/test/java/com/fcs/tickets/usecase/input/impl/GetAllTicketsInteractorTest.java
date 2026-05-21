package com.fcs.tickets.usecase.input.impl;

import com.fcs.tickets.usecase.exception.BusinessException;
import com.fcs.tickets.usecase.gateway.TicketGateway;
import com.fcs.tickets.usecase.model.TicketResponseModel;
import com.fcs.tickets.usecase.presenter.AllTicketsPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllTicketsInteractorTest {

    @Mock
    private AllTicketsPresenter allTicketsPresenter;

    @Mock
    private TicketGateway ticketGateway;

    private GetAllTicketsInteractor interactor;

    @BeforeEach
    void setUp() {
        interactor = new GetAllTicketsInteractor(allTicketsPresenter, ticketGateway);
    }

    @Test
    void shouldReturnAllTickets_whenTicketsExist() {
        // Given
        List<TicketResponseModel> tickets = List.of(
                new TicketResponseModel(1, "Show A", null, null),
                new TicketResponseModel(2, "Show B", null, null)
        );
        when(ticketGateway.getAll()).thenReturn(tickets);
        when(allTicketsPresenter.prepareSuccessView(tickets)).thenReturn(tickets);

        // When
        List<TicketResponseModel> result = interactor.execute();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(allTicketsPresenter).prepareSuccessView(tickets);
        verify(allTicketsPresenter, never()).prepareFailView(any());
    }

    @Test
    void shouldReturnEmptyList_whenNoTicketsExist() {
        // Given
        List<TicketResponseModel> emptyList = List.of();
        when(ticketGateway.getAll()).thenReturn(emptyList);
        when(allTicketsPresenter.prepareSuccessView(emptyList)).thenReturn(emptyList);

        // When
        List<TicketResponseModel> result = interactor.execute();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(allTicketsPresenter).prepareSuccessView(emptyList);
        verify(allTicketsPresenter, never()).prepareFailView(any());
    }

    @Test
    void shouldThrowBusinessException_whenGatewayThrowsAnException() {
        // Given
        String expectedMessage = "Ocorreu um erro interno ao obter todos os ingressos cadastrados.";
        when(ticketGateway.getAll()).thenThrow(new RuntimeException("DB error"));
        doThrow(new BusinessException(expectedMessage))
                .when(allTicketsPresenter).prepareFailView(expectedMessage);

        // When / Then
        BusinessException exception = assertThrows(BusinessException.class, () -> interactor.execute());

        assertEquals(expectedMessage, exception.getMessage());
        verify(allTicketsPresenter).prepareFailView(expectedMessage);
    }
}
