package com.fcs.orders.usecase.input;

public interface VerifyIfOnlyOneOrderIsInProgressInputBoundary {

    boolean execute(Integer customerId);
}
