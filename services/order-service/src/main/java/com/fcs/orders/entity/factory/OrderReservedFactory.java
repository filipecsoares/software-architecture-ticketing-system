package com.fcs.orders.entity.factory;

import com.fcs.orders.entity.Order;
import com.fcs.orders.entity.vo.OrderItem;
import com.fcs.orders.entity.vo.OrderStatus;

import java.util.List;
import java.util.Map;

public class OrderReservedFactory {

    public Order create(Integer eventId, Integer sessionId, Integer roomId, Map<Integer, List<String>> ticketsByChairs, Integer customerId, Double totalPrice) {
        OrderItem item = new OrderItem(eventId, sessionId, roomId, ticketsByChairs);
        Order toReserve = new Order();
        toReserve.setItem(item);
        toReserve.setCustomerId(customerId);
        toReserve.setTotalPrice(totalPrice);
        toReserve.setStatus(OrderStatus.RESERVED);
        if (toReserve.isValidToReserveOrder()) {
            return toReserve;
        }
        return new Order();
    }
}
