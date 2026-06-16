package com.fcs.customers.adapter.presenter;

import com.fcs.customers.usecase.exception.BusinessException;
import com.fcs.customers.usecase.model.PaymentRequestedResponseModel;
import com.fcs.customers.usecase.presenter.CustomerOrderReservationPaymentRequestPresenter;
import org.springframework.stereotype.Component;

@Component
public class CustomerOrderReservationPaymentRequestedPresenter implements CustomerOrderReservationPaymentRequestPresenter {
    @Override
    public PaymentRequestedResponseModel prepareSuccessView(PaymentRequestedResponseModel responseModel) {
        return responseModel;
    }

    @Override
    public void prepareFailView(String error) {
        throw new BusinessException(error);
    }
}
