package com.fcs.admin.adapter.entrypoint;

import com.fcs.admin.usecase.input.CreateEventInputBoundary;
import com.fcs.admin.usecase.input.DeleteEventInputBoundary;
import com.fcs.admin.usecase.input.GetAllEventsInputBoundary;
import com.fcs.admin.usecase.model.EventCreateModel;
import com.fcs.admin.usecase.model.EventCreatedResponseModel;
import com.fcs.admin.usecase.model.EventResponseModel;
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
public class AdminEventsRestController {

    private final CreateEventInputBoundary createEventInputBoundary;
    private final GetAllEventsInputBoundary getAllEventsInputBoundary;
    private final DeleteEventInputBoundary deleteEventInputBoundary;

    @PostMapping("/admin/event")
    @PreAuthorize("hasRole('admin-operations')")
    @Operation(summary = "Create a new event",
            description = "Given valid payload, create a new event and return the new room ID",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<EventCreatedResponseModel> createEvent(@RequestBody EventCreateModel eventCreateModel) {
        EventCreatedResponseModel eventCreatedResponseModel = createEventInputBoundary.execute(eventCreateModel);
        return new ResponseEntity<>(eventCreatedResponseModel, HttpStatus.CREATED);
    }

    @GetMapping("/admin/events")
    @PreAuthorize("hasRole('admin-operations')")
    @Operation(summary = "Get all events details",
            description = "Given valid payload, return all events details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<List<EventResponseModel>> getAllEvents() {
        List<EventResponseModel> allEvents = getAllEventsInputBoundary.execute();
        return new ResponseEntity<>(allEvents, HttpStatus.OK);
    }

    @DeleteMapping("/admin/event/{id}")
    @PreAuthorize("hasRole('admin-operations')")
    @Operation(summary = "Delete the event and its associate data",
            description = "Given valid payload, delete the event and its associate data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") Integer eventId) {
        deleteEventInputBoundary.execute(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
