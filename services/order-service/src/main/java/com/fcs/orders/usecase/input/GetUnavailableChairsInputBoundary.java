package com.fcs.orders.usecase.input;

import com.fcs.orders.usecase.model.UnavailableChairsResponseModel;

public interface GetUnavailableChairsInputBoundary {

    UnavailableChairsResponseModel execute(Integer sessionId);
}
