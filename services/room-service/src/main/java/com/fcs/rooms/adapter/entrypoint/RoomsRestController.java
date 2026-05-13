package com.fcs.rooms.adapter.entrypoint;

import com.fcs.rooms.adapter.entrypoint.dto.AllocateRoomDto;
import com.fcs.rooms.adapter.entrypoint.dto.CreateRoomDto;
import com.fcs.rooms.adapter.mapper.IRoomMapper;
import com.fcs.rooms.usecase.input.*;
import com.fcs.rooms.usecase.model.RoomCreatedResponseModel;
import com.fcs.rooms.usecase.model.RoomResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomsRestController {
    private final CreateRoomInputBoundary createRoomInputBoundary;
    private final AllocateRoomInputBoundary allocateRoomInputBoundary;
    private final GetAllRoomsInputBoundary getAllRoomsInputBoundary;
    private final GetRoomInputBoundary getRoomInputBoundary;
    private final DeleteRoomInputBoundary deleteRoomInputBoundary;
    private final IRoomMapper mapper;

    public RoomsRestController(final CreateRoomInputBoundary createRoomInputBoundary, final AllocateRoomInputBoundary allocateRoomInputBoundary, final GetAllRoomsInputBoundary getAllRoomsInputBoundary, final GetRoomInputBoundary getRoomInputBoundary, final DeleteRoomInputBoundary deleteRoomInputBoundary, final IRoomMapper mapper) {
        this.createRoomInputBoundary = createRoomInputBoundary;
        this.allocateRoomInputBoundary = allocateRoomInputBoundary;
        this.getAllRoomsInputBoundary = getAllRoomsInputBoundary;
        this.getRoomInputBoundary = getRoomInputBoundary;
        this.deleteRoomInputBoundary = deleteRoomInputBoundary;
        this.mapper = mapper;
    }

    @PostMapping("/room")
    @Operation(summary = "Create a new room",
            description = "Given valid payload, create a new room and return the new room ID",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<RoomCreatedResponseModel> create(@RequestBody CreateRoomDto requestDto) {
        RoomCreatedResponseModel roomCreatedResponseModel = createRoomInputBoundary.execute(mapper.createRoomDtoToRoom(requestDto));
        return new ResponseEntity<>(roomCreatedResponseModel, HttpStatus.CREATED);
    }

    @PostMapping("/room/{id}/allocation")
    @Operation(summary = "Create allocation date time to a room",
            description = "Given valid payload, allocate date time to a room",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<Void> allocate(@PathVariable("id") Integer roomId, @RequestBody AllocateRoomDto requestDto) {
        allocateRoomInputBoundary.execute(roomId, requestDto.startDateTime(), requestDto.endDateTime());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/rooms")
    @Operation(summary = "Get all rooms details",
            description = "Given valid payload, return all rooms details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<List<RoomResponseModel>> getAll() {
        List<RoomResponseModel> allRooms = getAllRoomsInputBoundary.execute();
        return new ResponseEntity<>(allRooms, HttpStatus.OK);
    }

    @GetMapping("/room/{id}")
    @Operation(summary = "Get room details",
            description = "Given valid payload, return room details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<RoomResponseModel> getRoom(@PathVariable("id") Integer roomId) {
        RoomResponseModel room = getRoomInputBoundary.execute(roomId);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping("/room/{id}")
    @Operation(summary = "Delete the room and its associate data",
            description = "Given valid payload, delete the room and its associate data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") Integer roomId) {
        deleteRoomInputBoundary.execute(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
