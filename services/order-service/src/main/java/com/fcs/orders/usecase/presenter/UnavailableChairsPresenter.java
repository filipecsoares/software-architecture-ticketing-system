package com.fcs.orders.usecase.presenter;

import com.fcs.orders.usecase.model.UnavailableChairsResponseModel;

public interface UnavailableChairsPresenter {
    UnavailableChairsResponseModel prepareSuccessView(UnavailableChairsResponseModel unavailableChairsResponseModel);

    void prepareFailView(String error);
}
