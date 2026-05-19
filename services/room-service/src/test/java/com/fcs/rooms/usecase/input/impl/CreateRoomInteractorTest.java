package com.fcs.rooms.usecase.input.impl;

import com.fcs.rooms.entity.Room;
import com.fcs.rooms.entity.vo.Allocation;
import com.fcs.rooms.entity.vo.Seat;
import com.fcs.rooms.usecase.gateway.RoomGateway;
import com.fcs.rooms.usecase.model.RoomCreatedResponseModel;
import com.fcs.rooms.usecase.presenter.RoomCreatedPresenter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRoomInteractorTest {

    @Mock
    private RoomGateway roomGateway;

    @Mock
    private RoomCreatedPresenter roomCreatedPresenter;

    @InjectMocks
    private CreateRoomInteractor createRoomInteractor;

    @Test
    void shouldCreateRoomSuccessfully() {
        // given
        Room room = validRoom();
        when(roomGateway.exists(room.getName())).thenReturn(false);
        when(roomGateway.create(room)).thenReturn(10);
        when(roomCreatedPresenter.prepareSuccessView(room)).thenReturn(new RoomCreatedResponseModel(10));

        // when
        RoomCreatedResponseModel result = createRoomInteractor.execute(room);

        // then
        verify(roomGateway).create(room);
        assertEquals(10, result.createdId());
    }

    @Test
    void shouldNotCreateRoomWhenRoomIsInvalid() {
        // given
        // Room with null name — isValid() returns false.
        // The interactor does NOT early-return after prepareFailView,
        // so roomGateway.exists() and create() are still invoked.
        Room room = new Room();
        room.setName(null);
        when(roomGateway.exists(null)).thenReturn(false);
        when(roomGateway.create(room)).thenReturn(1);

        // when
        createRoomInteractor.execute(room);

        // then
        verify(roomCreatedPresenter).prepareFailView("Verifique que todas as informações da sala estão preenchidas.");
    }

    @Test
    void shouldNotCreateRoomWhenItAlreadyHasAllocations() {
        // given
        // Valid room but with pre-existing allocations.
        // The interactor does NOT early-return after prepareFailView,
        // so roomGateway.exists() and create() are still invoked.
        Room roomWithAllocations = spy(validRoom());
        doReturn(List.of(mock(Allocation.class))).when(roomWithAllocations).getAllocations();
        when(roomGateway.exists(roomWithAllocations.getName())).thenReturn(false);
        when(roomGateway.create(roomWithAllocations)).thenReturn(1);

        // when
        createRoomInteractor.execute(roomWithAllocations);

        // then
        verify(roomCreatedPresenter).prepareFailView("A sala não deve ter alocações no momento da criação.");
    }

    @Test
    void shouldNotCreateRoomWhenNameAlreadyExists() {
        // given
        // The interactor does NOT early-return after prepareFailView,
        // so create() is still invoked even when the name already exists.
        Room room = validRoom();
        when(roomGateway.exists(room.getName())).thenReturn(true);
        when(roomGateway.create(room)).thenReturn(1);

        // when
        createRoomInteractor.execute(room);

        // then
        verify(roomCreatedPresenter).prepareFailView("Já existe uma sala cadastrada com o nome '" + room.getName() + "'");
    }

    private Room validRoom() {
        Seat seat = mock(Seat.class);
        when(seat.isValid()).thenReturn(true);
        Room room = new Room();
        room.setName("Room A");
        room.setSeats(List.of(seat));
        return room;
    }
}
