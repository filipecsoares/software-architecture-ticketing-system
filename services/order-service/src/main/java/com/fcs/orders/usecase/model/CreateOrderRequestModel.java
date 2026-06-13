package com.fcs.orders.usecase.model;

import com.fcs.orders.entity.vo.OrderItem;
import com.fcs.orders.entity.vo.OrderPayment;

public record CreateOrderRequestModel(Integer customerId, OrderItem item, Double totalPrice, OrderPayment payment) {
}
