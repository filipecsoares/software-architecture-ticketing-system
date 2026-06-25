package com.fcs.admin.adapter.entrypoint;

import com.fcs.admin.usecase.input.CreateTicketInputBoundary;
import com.fcs.admin.usecase.input.DeleteTicketInputBoundary;
import com.fcs.admin.usecase.input.GetAllTicketsInputBoundary;
import com.fcs.admin.usecase.model.CreateTicketModel;
import com.fcs.admin.usecase.model.TicketCreatedResponseModel;
import com.fcs.admin.usecase.model.TicketResponseModel;
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
public class AdminTicketsRestController {

    private final CreateTicketInputBoundary createTicketInputBoundary;
    private final GetAllTicketsInputBoundary getAllTicketsInputBoundary;
    private final DeleteTicketInputBoundary deleteTicketInputBoundary;

    @PostMapping("/admin/ticket")
    @PreAuthorize("hasRole('admin-operations')")
    @Operation(summary = "Create a new ticket",
            description = "Given valid payload, create a new ticket and return the new ticket ID",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<TicketCreatedResponseModel> createTicket(@RequestBody CreateTicketModel createTicketModel) {
        TicketCreatedResponseModel ticketCreatedResponseModel = createTicketInputBoundary.execute(createTicketModel);
        return new ResponseEntity<>(ticketCreatedResponseModel, HttpStatus.CREATED);
    }

    @GetMapping("/admin/tickets")
    @PreAuthorize("hasRole('admin-operations')")
    @Operation(summary = "Get all tickets details",
            description = "Given valid payload, return all tickets details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<List<TicketResponseModel>> getAllTickets() {
        List<TicketResponseModel> allTickets = getAllTicketsInputBoundary.execute();
        return new ResponseEntity<>(allTickets, HttpStatus.OK);
    }

    @DeleteMapping("/admin/ticket/{id}")
    @PreAuthorize("hasRole('admin-operations')")
    @Operation(summary = "Delete the ticket and its associate data",
            description = "Given valid payload, delete the ticket and its associate data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") Integer ticketId) {
        deleteTicketInputBoundary.execute(ticketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
