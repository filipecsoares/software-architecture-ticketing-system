package com.fcs.orders.usecase.input;

public interface GetTotalUnavailableTicketsInputBoundary {

    Integer execute(Integer sessionId, Integer ticketId);
}
