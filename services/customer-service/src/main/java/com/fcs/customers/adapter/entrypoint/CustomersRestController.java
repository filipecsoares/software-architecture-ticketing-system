package com.fcs.customers.adapter.entrypoint;

import com.fcs.customers.adapter.entrypoint.dto.CreateCustomerDto;
import com.fcs.customers.adapter.entrypoint.dto.CreateOrderReservationDto;
import com.fcs.customers.adapter.entrypoint.dto.PaymentRequestedDto;
import com.fcs.customers.adapter.mapper.ICustomerMapper;
import com.fcs.customers.usecase.input.*;
import com.fcs.customers.usecase.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomersRestController {

    private final ListEventsToCustomerInputBoundary listEventsToCustomerInputBoundary;
    private final ListTicketsToCustomerInputBoundary listTicketsToCustomerInputBoundary;
    private final ListChairsWithAvailabilityToCustomerInputBoundary listChairsWithAvailabilityToCustomerInputBoundary;

    private final CreateCustomerOrderReservationInputBoundary createCustomerOrderReservationInputBoundary;

    private final CreateCustomerOrderReservationPaymentRequestInputBoundary createCustomerOrderPaymentRequestInputBoundary;

    private final GetCustomerOrdersInputBoundary getCustomerOrdersInputBoundary;

    private final ICustomerMapper mapper;

    // Fluxo de listagem de eventos

    @GetMapping("/customer/events")
    @PreAuthorize("hasRole('get-operations')")
    @Operation(summary = "List events to the customer",
            description = "Given valid payload, return events details to the customer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<List<EventCustomerResponseModel>> listEvents() {
        List<EventCustomerResponseModel> events = listEventsToCustomerInputBoundary.execute();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/customer/tickets")
    @PreAuthorize("hasRole('get-operations')")
    @Operation(summary = "List ticket details to the customer",
            description = "Given valid payload, return ticket details to the customer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<List<TicketCustomerResponseModel>> listTickets(@RequestParam("ids") List<Integer> ticketIds) {
        List<TicketCustomerResponseModel> tickets = listTicketsToCustomerInputBoundary.execute(ticketIds);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/customer/chairs")
    @PreAuthorize("hasRole('get-operations')")
    @Operation(summary = "List chairs with availability for session to the customer",
            description = "Given valid payload, return chairs with availability for session to the customer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<RoomDetailsWithChairsAvailabilityToCustomerResponseModel> listChairs(@RequestParam("roomId") Integer roomId,
                                                                                               @RequestParam("sessionId") Integer sessionId) {
        RoomDetailsWithChairsAvailabilityToCustomerResponseModel response
                = listChairsWithAvailabilityToCustomerInputBoundary.execute(roomId, sessionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Fluxo de reserva de pedido


    @PostMapping("/customer/reservation")
    @PreAuthorize("hasRole('get-operations')")
    @Operation(summary = "Create a new order reservation",
            description = "Given valid payload, create a new order reservation and return reservation ID",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<Integer> createReservation(@AuthenticationPrincipal Jwt jwt, @RequestBody CreateOrderReservationDto requestDto) {
        String customerId = jwt.getSubject();
        Integer reservationId = createCustomerOrderReservationInputBoundary.execute(mapper.createOrderReservationDtoToCreateOrderReservationRequestModel(requestDto), customerId);
        return new ResponseEntity<>(reservationId, HttpStatus.CREATED);
    }

    // Fluxo de pagamento da reserva do pedido

    @PostMapping("/customer/payment")
    @PreAuthorize("hasRole('get-operations')")
    @Operation(summary = "Create a new payment request for reservation",
            description = "Given valid payload, create a new payment request for reservation and return the request result",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<PaymentRequestedResponseModel> createReservationPaymentRequest(@RequestBody PaymentRequestedDto requestDto) {
        PaymentRequestedResponseModel responseModel = createCustomerOrderPaymentRequestInputBoundary.execute(
                requestDto.reservationId(),
                requestDto.cardNumber(),
                requestDto.cvv(),
                requestDto.cardHolderName(),
                requestDto.exp(),
                requestDto.banner());
        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
    }


    @GetMapping("/customer/orders")
    @PreAuthorize("hasRole('get-operations')")
    @Operation(summary = "List all orders from customer",
            description = "Given valid payload, return all orders from customer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<List<OrderDetailResponseModel>> getCustomerAllOrders(@AuthenticationPrincipal Jwt jwt) {
        String customerId = jwt.getSubject();
        List<OrderDetailResponseModel> orders = getCustomerOrdersInputBoundary.execute(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
