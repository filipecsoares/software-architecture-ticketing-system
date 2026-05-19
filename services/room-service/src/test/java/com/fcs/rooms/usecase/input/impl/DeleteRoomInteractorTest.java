package com.fcs.rooms.usecase.input.impl;

import com.fcs.rooms.usecase.gateway.RoomGateway;
import com.fcs.rooms.usecase.presenter.RoomDeletedPresenter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRoomInteractorTest {

    @Mock
    private RoomGateway roomGateway;

    @Mock
    private RoomDeletedPresenter roomDeletedPresenter;

    @InjectMocks
    private DeleteRoomInteractor deleteRoomInteractor;

    @Test
    void shouldDeleteRoomSuccessfully() {
        // given
        Integer roomId = 1;

        // when
        deleteRoomInteractor.execute(roomId);

        // then
        verify(roomGateway).delete(roomId);
        verify(roomDeletedPresenter, never()).prepareFailView(anyString());
    }

    @Test
    void shouldNotDeleteRoomWhenGatewayThrowsException() {
        // given
        Integer roomId = 1;
        doThrow(new RuntimeException("DB error")).when(roomGateway).delete(roomId);

        // when
        deleteRoomInteractor.execute(roomId);

        // then
        verify(roomDeletedPresenter).prepareFailView("Ocorreu um erro interno ao excluir a sala.");
    }
}
