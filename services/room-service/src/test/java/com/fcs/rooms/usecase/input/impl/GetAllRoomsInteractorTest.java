package com.fcs.rooms.usecase.input.impl;

import com.fcs.rooms.usecase.gateway.RoomGateway;
import com.fcs.rooms.usecase.model.RoomResponseModel;
import com.fcs.rooms.usecase.presenter.AllRoomsPresenter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllRoomsInteractorTest {

    @Mock
    private RoomGateway roomGateway;

    @Mock
    private AllRoomsPresenter allRoomsPresenter;

    @InjectMocks
    private GetAllRoomsInteractor getAllRoomsInteractor;

    @Test
    void shouldReturnAllRoomsSuccessfully() {
        // given
        List<RoomResponseModel> rooms = List.of(
                new RoomResponseModel(1, "Room A", List.of(), List.of()),
                new RoomResponseModel(2, "Room B", List.of(), List.of())
        );
        when(roomGateway.getAll()).thenReturn(rooms);
        when(allRoomsPresenter.prepareSuccessView(rooms)).thenReturn(rooms);

        // when
        List<RoomResponseModel> result = getAllRoomsInteractor.execute();

        // then
        verify(roomGateway).getAll();
        verify(allRoomsPresenter).prepareSuccessView(rooms);
        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnEmptyListWhenNoRoomsExist() {
        // given
        when(roomGateway.getAll()).thenReturn(List.of());
        when(allRoomsPresenter.prepareSuccessView(List.of())).thenReturn(List.of());

        // when
        List<RoomResponseModel> result = getAllRoomsInteractor.execute();

        // then
        assertEquals(0, result.size());
        verify(allRoomsPresenter, never()).prepareFailView(anyString());
    }

    @Test
    void shouldNotReturnRoomsWhenGatewayThrowsException() {
        // given
        when(roomGateway.getAll()).thenThrow(new RuntimeException("DB error"));
        when(allRoomsPresenter.prepareSuccessView(anyList())).thenReturn(List.of());

        // when
        getAllRoomsInteractor.execute();

        // then
        verify(allRoomsPresenter).prepareFailView("Ocorreu um erro interno ao obter todas as salas cadastradas.");
    }
}
