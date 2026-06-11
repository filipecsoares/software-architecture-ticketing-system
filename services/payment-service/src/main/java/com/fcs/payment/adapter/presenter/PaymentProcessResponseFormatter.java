package com.fcs.payment.adapter.presenter;

import com.fcs.payment.usecase.exception.BusinessException;
import com.fcs.payment.usecase.model.PaymentProcessedResponseModel;
import com.fcs.payment.usecase.presenter.PaymentProcessPresenter;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessResponseFormatter implements PaymentProcessPresenter {

    @Override
    public PaymentProcessedResponseModel prepareSuccessView(PaymentProcessedResponseModel responseModel) {
        return responseModel;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
