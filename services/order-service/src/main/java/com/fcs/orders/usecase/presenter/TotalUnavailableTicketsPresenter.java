package com.fcs.orders.usecase.presenter;

public interface TotalUnavailableTicketsPresenter {
    Integer prepareSuccessView(Integer total);

    void prepareFailView(String s);
}
