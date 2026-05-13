package com.fcs.rooms.adapter.entrypoint;

import com.fcs.rooms.adapter.entrypoint.dto.AllocateRoomDto;
import com.fcs.rooms.adapter.entrypoint.dto.CreateRoomDto;
import com.fcs.rooms.adapter.entrypoint.dto.PositionDto;
import com.fcs.rooms.adapter.entrypoint.dto.SeatDto;
import com.fcs.rooms.adapter.mapper.IRoomMapper;
import com.fcs.rooms.usecase.input.*;
import com.fcs.rooms.usecase.model.RoomCreatedResponseModel;
import com.fcs.rooms.usecase.model.RoomResponseModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomsRestControllerTest {

    @Mock
    private CreateRoomInputBoundary createRoomInputBoundary;

    @Mock
    private AllocateRoomInputBoundary allocateRoomInputBoundary;

    @Mock
    private GetAllRoomsInputBoundary getAllRoomsInputBoundary;

    @Mock
    private GetRoomInputBoundary getRoomInputBoundary;

    @Mock
    private DeleteRoomInputBoundary deleteRoomInputBoundary;

    @Mock
    private IRoomMapper mapper;

    @InjectMocks
    private RoomsRestController controller;

    @Test
    void create_shouldReturnCreated() {
        CreateRoomDto dto = new CreateRoomDto("Room1", List.of(new SeatDto("Seat1", new PositionDto("A", 1))));
        RoomCreatedResponseModel response = new RoomCreatedResponseModel(1);

        when(mapper.createRoomDtoToRoom(dto)).thenReturn(null);
        when(createRoomInputBoundary.execute(any())).thenReturn(response);

        ResponseEntity<RoomCreatedResponseModel> result = controller.create(dto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(1, result.getBody().createdId());
        verify(createRoomInputBoundary).execute(any());
    }

    @Test
    void allocate_shouldReturnOk() {
        AllocateRoomDto dto = new AllocateRoomDto(LocalDateTime.now(), LocalDateTime.now().plusHours(1));

        ResponseEntity<Void> result = controller.allocate(1, dto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(allocateRoomInputBoundary).execute(eq(1), any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void getAll_shouldReturnOk() {
        List<RoomResponseModel> rooms = List.of(new RoomResponseModel(1, "Room1", List.of(), List.of()));

        when(getAllRoomsInputBoundary.execute()).thenReturn(rooms);

        ResponseEntity<List<RoomResponseModel>> result = controller.getAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
        assertEquals("Room1", result.getBody().get(0).name());
    }

    @Test
    void getRoom_shouldReturnOk() {
        RoomResponseModel room = new RoomResponseModel(1, "Room1", List.of(), List.of());

        when(getRoomInputBoundary.execute(1)).thenReturn(room);

        ResponseEntity<RoomResponseModel> result = controller.getRoom(1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Room1", result.getBody().name());
    }

    @Test
    void deleteRoom_shouldReturnNoContent() {
        ResponseEntity<Void> result = controller.deleteRoom(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(deleteRoomInputBoundary).execute(1);
    }
}
