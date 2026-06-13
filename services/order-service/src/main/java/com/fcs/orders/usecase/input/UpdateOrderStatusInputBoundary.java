package com.fcs.orders.usecase.input;

import com.fcs.orders.entity.vo.OrderStatus;

public interface UpdateOrderStatusInputBoundary {

    void execute(Integer orderId, OrderStatus status);
}
