package com.fcs.orders.adapter.entrypoint;

import com.fcs.orders.adapter.entrypoint.dto.CreateOrderReservationDto;
import com.fcs.orders.adapter.mapper.IOrderMapper;
import com.fcs.orders.usecase.input.CreateOrderReservationInputBoundary;
import com.fcs.orders.usecase.input.GetTotalUnavailableTicketsInputBoundary;
import com.fcs.orders.usecase.input.GetUnavailableChairsInputBoundary;
import com.fcs.orders.usecase.input.VerifyIfOnlyOneOrderIsInProgressInputBoundary;
import com.fcs.orders.usecase.model.CreatedOrderReservationResponseModel;
import com.fcs.orders.usecase.model.UnavailableChairsResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("orders")
@RequiredArgsConstructor
public class OrdersRestController {


    private final GetUnavailableChairsInputBoundary getUnavailableChairsInputBoundary;
    private final VerifyIfOnlyOneOrderIsInProgressInputBoundary verifyIfOnlyOneOrderIsInProgressInputBoundary;
    private final CreateOrderReservationInputBoundary createOrderReservationInputBoundary;
    private final GetTotalUnavailableTicketsInputBoundary getTotalUnavailableTicketsInputBoundary;

    private final IOrderMapper mapper;

    @GetMapping("/sessions/{sessionId}/unavailable/chairs")
    @Operation(summary = "Get unavailable chairs for session",
            description = "Given valid payload, return unavailable chairs for session",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<UnavailableChairsResponseModel> getUnavailableChairs(@PathVariable("sessionId") Integer sessionId) {
        UnavailableChairsResponseModel unavailableChairsResponseModel = getUnavailableChairsInputBoundary.execute(sessionId);
        return new ResponseEntity<>(unavailableChairsResponseModel, HttpStatus.OK);
    }

    @GetMapping("/{customerId}/in-progress")
    @Operation(summary = "Get if customer has one order already in progress",
            description = "Given valid payload, return if customer has one order already in progress",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<Boolean> hasOrderInProgress(@PathVariable("customerId") Integer customerId) {
        boolean hasOrderInProgress = verifyIfOnlyOneOrderIsInProgressInputBoundary.execute(customerId);
        return new ResponseEntity<>(hasOrderInProgress, HttpStatus.OK);
    }

    @GetMapping("/sessions/{sessionId}/unavailable/tickets/{ticketId}")
    @Operation(summary = "Return unavailable total tickets to ticket ID that has been reserved or bought for session",
            description = "Given valid payload, return unavailable total tickets to ticket ID that has been reserved or bought for session",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<Integer> getTotalUnavailableTicketsBy(@PathVariable("sessionId") Integer sessionId, @PathVariable Integer ticketId) {
        Integer totalUnavailableTickets = getTotalUnavailableTicketsInputBoundary.execute(sessionId, ticketId);
        return new ResponseEntity<>(totalUnavailableTickets, HttpStatus.OK);
    }

    @PostMapping("/reservation")
    @Operation(summary = "Create a new order reservation",
            description = "Given valid payload, create a new order reservation and return reservation ID",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<CreatedOrderReservationResponseModel> createReservation(@RequestBody CreateOrderReservationDto requestDto) {
        CreatedOrderReservationResponseModel createdOrderReservationResponseModel = createOrderReservationInputBoundary.execute(mapper.createOrderReservationDtoToCreateOrderReservationRequestModel(requestDto));
        return new ResponseEntity<>(createdOrderReservationResponseModel, HttpStatus.CREATED);
    }
}
