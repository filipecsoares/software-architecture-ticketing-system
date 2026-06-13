package com.fcs.orders.entity;

import com.fcs.orders.entity.vo.OrderItem;
import com.fcs.orders.entity.vo.OrderPayment;
import com.fcs.orders.entity.vo.OrderStatus;
import lombok.Data;

@Data
public class Order {

    private Integer id;
    private OrderItem item;
    private Integer customerId;
    private Double totalPrice;
    private OrderPayment payment;
    private OrderStatus status;

    public boolean isValidToReserveOrder() {
        return item != null && item.eventId() != null
                && item.sessionId() != null
                && item.roomId() != null
                && item.ticketsByChairs() != null
                && !item.ticketsByChairs().isEmpty()
                && totalPrice != null
                && customerId != null;
    }

    public boolean isValidToCreate() {
        return isValidToReserveOrder()
                && payment != null
                && status != null
                && status.equals(OrderStatus.PENDING);
    }
}
