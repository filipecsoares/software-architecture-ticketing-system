package com.fcs.orders.usecase.model;

import com.fcs.orders.entity.vo.OrderStatus;

public record CreatedOrderReservationResponseModel(Integer orderId, OrderStatus status) {

}
