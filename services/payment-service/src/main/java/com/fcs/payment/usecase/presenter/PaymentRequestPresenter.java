package com.fcs.payment.usecase.presenter;

import com.fcs.payment.usecase.model.PaymentRequestedResponseModel;

public interface PaymentRequestPresenter {

    PaymentRequestedResponseModel prepareSuccessView(PaymentRequestedResponseModel responseModel);

    void prepareFailView(String error);
}
