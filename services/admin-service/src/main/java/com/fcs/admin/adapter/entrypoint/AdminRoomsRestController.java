package com.fcs.admin.adapter.entrypoint;

import com.fcs.admin.usecase.input.CreateRoomInputBoundary;
import com.fcs.admin.usecase.input.DeleteRoomInputBoundary;
import com.fcs.admin.usecase.input.GetAllRoomsInputBoundary;
import com.fcs.admin.usecase.model.RoomCreateModel;
import com.fcs.admin.usecase.model.RoomCreatedResponseModel;
import com.fcs.admin.usecase.model.RoomResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminRoomsRestController {

    private final CreateRoomInputBoundary createRoomInputBoundary;
    private final GetAllRoomsInputBoundary getAllRoomsInputBoundary;
    private final DeleteRoomInputBoundary deleteRoomInputBoundary;

    @PostMapping("/admin/room")
    @PreAuthorize("hasRole('admin-operations')")
    @Operation(summary = "Create a new room",
            description = "Given valid payload, create a new room and return the new room ID",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<RoomCreatedResponseModel> createRoom(@RequestBody RoomCreateModel roomCreateModel) {
        RoomCreatedResponseModel roomCreatedResponseModel = createRoomInputBoundary.execute(roomCreateModel);
        return new ResponseEntity<>(roomCreatedResponseModel, HttpStatus.CREATED);
    }

    @GetMapping("/admin/rooms")
    @PreAuthorize("hasRole('admin-operations')")
    @Operation(summary = "Get all rooms details",
            description = "Given valid payload, return all rooms details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<List<RoomResponseModel>> getAllRooms() {
        List<RoomResponseModel> allRooms = getAllRoomsInputBoundary.execute();
        return new ResponseEntity<>(allRooms, HttpStatus.OK);
    }

    @DeleteMapping("/admin/room/{id}")
    @PreAuthorize("hasRole('admin-operations')")
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
