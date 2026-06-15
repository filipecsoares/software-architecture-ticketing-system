package com.fcs.customers.usecase.presenter;

import com.fcs.customers.usecase.model.PaymentRequestedResponseModel;

public interface CustomerOrderReservationPaymentRequestPresenter {

    PaymentRequestedResponseModel prepareSuccessView(PaymentRequestedResponseModel responseModel);

    void prepareFailView(String error);
}
