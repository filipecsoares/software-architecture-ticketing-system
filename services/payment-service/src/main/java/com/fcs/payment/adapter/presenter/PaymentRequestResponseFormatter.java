package com.fcs.payment.adapter.presenter;

import com.fcs.payment.usecase.exception.BusinessException;
import com.fcs.payment.usecase.model.PaymentRequestedResponseModel;
import com.fcs.payment.usecase.presenter.PaymentRequestPresenter;
import org.springframework.stereotype.Component;

@Component
public class PaymentRequestResponseFormatter implements PaymentRequestPresenter {

    @Override
    public PaymentRequestedResponseModel prepareSuccessView(PaymentRequestedResponseModel responseModel) {
        return responseModel;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
