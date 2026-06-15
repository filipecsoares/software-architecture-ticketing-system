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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomersRestController {

    private final CreateCustomerInputBoundary createCustomerInputBoundary;
    private final GetAllCustomersInputBoundary getAllCustomersInputBoundary;
    private final GetCustomerInputBoundary getCustomerInputBoundary;
    private final DeleteCustomerInputBoundary deleteCustomerInputBoundary;

    private final ListEventsToCustomerInputBoundary listEventsToCustomerInputBoundary;
    private final ListTicketsToCustomerInputBoundary listTicketsToCustomerInputBoundary;
    private final ListChairsWithAvailabilityToCustomerInputBoundary listChairsWithAvailabilityToCustomerInputBoundary;

    private final CreateCustomerOrderReservationInputBoundary createCustomerOrderReservationInputBoundary;

    private final CreateCustomerOrderReservationPaymentRequestInputBoundary createCustomerOrderPaymentRequestInputBoundary;

    private final GetCustomerOrdersInputBoundary getCustomerOrdersInputBoundary;

    private final ICustomerMapper mapper;

    // Fluxo de gestao de clientes

    @PostMapping("/customer")
    @Operation(summary = "Create a new customer",
            description = "Given valid payload, create a new customer and return the new customer ID",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<CustomerCreatedResponseModel> create(@RequestBody CreateCustomerDto requestDto) {
        CustomerCreatedResponseModel customerCreatedResponseModel = createCustomerInputBoundary.execute(mapper.createCustomerDtoToCustomer(requestDto));
        return new ResponseEntity<>(customerCreatedResponseModel, HttpStatus.CREATED);
    }

    @GetMapping("/customers")
    @Operation(summary = "Get all customers details",
            description = "Given valid payload, return all customers details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<List<CustomerResponseModel>> getAll() {
        List<CustomerResponseModel> allCustomers = getAllCustomersInputBoundary.execute();
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    @Operation(summary = "Get customer details",
            description = "Given valid payload, return customer details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<CustomerResponseModel> getCustomer(@PathVariable("id") Integer customerId) {
        CustomerResponseModel customer = getCustomerInputBoundary.execute(customerId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/customer/{id}")
    @Operation(summary = "Delete the customer and its associate data",
            description = "Given valid payload, delete the customer and its associate data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Integer customerId) {
        deleteCustomerInputBoundary.execute(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Fluxo de listagem de eventos

    @GetMapping("/customer/events")
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


    @PostMapping("/customer/{customerId}/reservation") // TODO ver isso aq... nao to usando PathVariable para obter o customerId
    @Operation(summary = "Create a new order reservation",
            description = "Given valid payload, create a new order reservation and return reservation ID",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<Integer> createReservation(@RequestBody CreateOrderReservationDto requestDto) {
        Integer reservationId = createCustomerOrderReservationInputBoundary.execute(mapper.createOrderReservationDtoToCreateOrderReservationRequestModel(requestDto));
        return new ResponseEntity<>(reservationId, HttpStatus.CREATED);
    }

    // Fluxo de pagamento da reserva do pedido

    @PostMapping("/customer/payment")
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

    // Listagem dos pedidos


    @GetMapping("/customer/{customerId}/orders") // TODO futuramente trocar pelo access token e nao passar customerId na url em rota q front vai chamar
    @Operation(summary = "List all orders from customer",
            description = "Given valid payload, return all orders from customer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<List<OrderDetailResponseModel>> getCustomerAllOrders(@PathVariable("customerId") Integer customerId) {
        List<OrderDetailResponseModel> orders = getCustomerOrdersInputBoundary.execute(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
