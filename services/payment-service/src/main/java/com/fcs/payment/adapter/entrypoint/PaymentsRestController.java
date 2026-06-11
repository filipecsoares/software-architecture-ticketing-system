package com.fcs.payment.adapter.entrypoint;

import com.fcs.payment.adapter.entrypoint.dto.PaymentRequestedDto;
import com.fcs.payment.entity.PaymentData;
import com.fcs.payment.entity.vo.CardBanner;
import com.fcs.payment.usecase.input.CreatePaymentRequestInputBoundary;
import com.fcs.payment.usecase.model.PaymentRequestedResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentsRestController {

    private final CreatePaymentRequestInputBoundary createPaymentRequestInputBoundary;

    @PostMapping("/reservation-payment")
    @Operation(summary = "Create a new payment request for reservation",
            description = "Given valid payload, create a new payment request for reservation and return the request result",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "422", description = "When some business error occur"),
                    @ApiResponse(responseCode = "500", description = "When an internal error occur")
            })
    public ResponseEntity<PaymentRequestedResponseModel> createReservationPaymentRequest(@RequestBody PaymentRequestedDto requestDto) {
        PaymentRequestedResponseModel responseModel = createPaymentRequestInputBoundary.execute(requestDto.reservationId(), new PaymentData(
                requestDto.cardNumber(),
                requestDto.cvv(),
                requestDto.cardHolderName(),
                requestDto.exp(),
                CardBanner.valueOf(requestDto.banner())));
        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
    }
}
