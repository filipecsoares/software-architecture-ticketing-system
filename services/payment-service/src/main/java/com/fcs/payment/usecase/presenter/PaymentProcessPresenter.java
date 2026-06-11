package com.fcs.payment.usecase.presenter;

import com.fcs.payment.usecase.model.PaymentProcessedResponseModel;

public interface PaymentProcessPresenter {

    PaymentProcessedResponseModel prepareSuccessView(PaymentProcessedResponseModel responseModel);

    void prepareFailView(String error);
}
