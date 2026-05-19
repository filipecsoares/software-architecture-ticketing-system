package com.fcs.rooms.usecase.input.impl;

import com.fcs.rooms.usecase.gateway.RoomGateway;
import com.fcs.rooms.usecase.model.AllocationResponseModel;
import com.fcs.rooms.usecase.model.RoomResponseModel;
import com.fcs.rooms.usecase.presenter.RoomAllocatedPresenter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AllocateRoomInteractorTest {

    @Mock
    private RoomGateway roomGateway;

    @Mock
    private RoomAllocatedPresenter roomAllocatedPresenter;

    @InjectMocks
    private AllocateRoomInteractor allocateRoomInteractor;

    @Test
    void shouldAllocateRoomSuccessfully() {
        // given
        Integer roomId = 1;
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(3);
        RoomResponseModel room = new RoomResponseModel(roomId, "Room A", List.of(), List.of());
        when(roomGateway.getById(roomId)).thenReturn(room);

        // when
        allocateRoomInteractor.execute(roomId, start, end);

        // then
        verify(roomGateway).allocate(roomId, start, end);
        verify(roomAllocatedPresenter, never()).prepareFailView(anyString());
    }

    @Test
    void shouldNotAllocateRoomWhenRoomDoesNotExist() {
        // given
        Integer roomId = 99;
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(3);
        when(roomGateway.getById(roomId)).thenReturn(null);

        // when
        allocateRoomInteractor.execute(roomId, start, end);

        // then
        verify(roomAllocatedPresenter).prepareFailView("A Sala com ID '" + roomId + "' não existe.");
    }

    @Test
    void shouldNotAllocateRoomWhenDateRangeIsInvalid() {
        // given
        Integer roomId = 1;
        LocalDateTime start = LocalDateTime.now().plusHours(3);
        LocalDateTime end = LocalDateTime.now().plusHours(1);
        RoomResponseModel room = new RoomResponseModel(roomId, "Room A", List.of(), List.of());
        when(roomGateway.getById(roomId)).thenReturn(room);

        // when
        allocateRoomInteractor.execute(roomId, start, end);

        // then
        verify(roomAllocatedPresenter).prepareFailView("O intervalo de datas não é válido");
    }

    @Test
    void shouldNotAllocateRoomWhenDateRangeOverlapsExistingAllocation() {
        // given
        Integer roomId = 1;
        LocalDateTime start = LocalDateTime.now().plusHours(2);
        LocalDateTime end = LocalDateTime.now().plusHours(4);
        LocalDateTime existingStart = LocalDateTime.now().plusHours(1);
        LocalDateTime existingEnd = LocalDateTime.now().plusHours(5);
        AllocationResponseModel existingAllocation = new AllocationResponseModel(10, existingStart, existingEnd);
        RoomResponseModel room = new RoomResponseModel(roomId, "Room A", List.of(), List.of(existingAllocation));
        when(roomGateway.getById(roomId)).thenReturn(room);

        // when
        allocateRoomInteractor.execute(roomId, start, end);

        // then
        verify(roomAllocatedPresenter).prepareFailView("A Sala já está reservada neste range de data e hora.");
    }

    @Test
    void shouldNotAllocateRoomWhenGatewayThrowsException() {
        // given
        Integer roomId = 1;
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(3);
        RoomResponseModel room = new RoomResponseModel(roomId, "Room A", List.of(), List.of());
        when(roomGateway.getById(roomId)).thenReturn(room);
        doThrow(new RuntimeException("DB error")).when(roomGateway).allocate(roomId, start, end);

        // when
        allocateRoomInteractor.execute(roomId, start, end);

        // then
        verify(roomAllocatedPresenter).prepareFailView("Ocorreu um erro interno ao alocar a sala.");
    }
}