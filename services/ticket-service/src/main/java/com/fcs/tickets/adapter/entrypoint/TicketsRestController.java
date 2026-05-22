package com.fcs.tickets.adapter.entrypoint;

import com.fcs.tickets.adapter.entrypoint.dto.CreateTicketDto;
import com.fcs.tickets.adapter.mapper.ITicketMapper;
import com.fcs.tickets.usecase.input.CreateTicketInputBoundary;
import com.fcs.tickets.usecase.input.DeleteTicketInputBoundary;
import com.fcs.tickets.usecase.input.GetAllTicketsInputBoundary;
import com.fcs.tickets.usecase.input.GetTicketInputBoundary;
import com.fcs.tickets.usecase.model.TicketCreatedResponseModel;
import com.fcs.tickets.usecase.model.TicketResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketsRestController {

    private final CreateTicketInputBoundary createTicketInputBoundary;
    private final GetAllTicketsInputBoundary getAllTicketsInputBoundary;
    private final GetTicketInputBoundary getTicketInputBoundary;
    private final DeleteTicketInputBoundary deleteTicketInputBoundary;
    private final ITicketMapper mapper;

    @PostMapping("/ticket")
    @Operation(summary = "Create a new ticket",
            description = "Given valid payload, create a new ticket and return the new ticket ID",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<TicketCreatedResponseModel> create(@RequestBody CreateTicketDto requestDto) {
        TicketCreatedResponseModel ticketCreatedResponseModel = createTicketInputBoundary.execute(mapper.createTicketDtoToTicket(requestDto));
        return new ResponseEntity<>(ticketCreatedResponseModel, HttpStatus.CREATED);
    }

    @GetMapping("/tickets")
    @Operation(summary = "Get all tickets details",
            description = "Given valid payload, return all tickets details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<List<TicketResponseModel>> getAll() {
        List<TicketResponseModel> allTickets = getAllTicketsInputBoundary.execute();
        return new ResponseEntity<>(allTickets, HttpStatus.OK);
    }

    @GetMapping("/ticket/{id}")
    @Operation(summary = "Get ticket details",
            description = "Given valid payload, return ticket details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<TicketResponseModel> getTicket(@PathVariable("id") Integer ticketId) {
        TicketResponseModel ticket = getTicketInputBoundary.execute(ticketId);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @DeleteMapping("/ticket/{id}")
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
