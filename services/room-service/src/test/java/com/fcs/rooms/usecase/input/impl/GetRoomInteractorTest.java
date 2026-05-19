package com.fcs.rooms.usecase.input.impl;

import com.fcs.rooms.usecase.gateway.RoomGateway;
import com.fcs.rooms.usecase.model.RoomResponseModel;
import com.fcs.rooms.usecase.presenter.RoomPresenter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetRoomInteractorTest {

    @Mock
    private RoomGateway roomGateway;

    @Mock
    private RoomPresenter roomPresenter;

    @InjectMocks
    private GetRoomInteractor getRoomInteractor;

    @Test
    void shouldReturnRoomSuccessfully() {
        // given
        Integer roomId = 1;
        RoomResponseModel room = new RoomResponseModel(roomId, "Room A", List.of(), List.of());
        when(roomGateway.getById(roomId)).thenReturn(room);
        when(roomPresenter.prepareSuccessView(room)).thenReturn(room);

        // when
        RoomResponseModel result = getRoomInteractor.execute(roomId);

        // then
        verify(roomGateway).getById(roomId);
        verify(roomPresenter).prepareSuccessView(room);
        assertEquals(roomId, result.id());
        assertEquals("Room A", result.name());
    }

    @Test
    void shouldReturnNullWhenGatewayThrowsException() {
        // given
        Integer roomId = 1;
        when(roomGateway.getById(roomId)).thenThrow(new RuntimeException("DB error"));

        // when
        RoomResponseModel result = getRoomInteractor.execute(roomId);

        // then
        verify(roomPresenter).prepareFailView("Ocorreu um erro interno ao obter os dados da sala.");
        assertNull(result);
    }
}
