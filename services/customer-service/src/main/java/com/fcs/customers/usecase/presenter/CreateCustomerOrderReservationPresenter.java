package com.fcs.customers.usecase.presenter;

public interface CreateCustomerOrderReservationPresenter {
    Integer prepareSuccessView(Integer reservationId);

    void prepareFailView(String error);
}
