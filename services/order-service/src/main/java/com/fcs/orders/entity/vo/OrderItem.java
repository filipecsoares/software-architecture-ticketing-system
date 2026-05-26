package com.fcs.orders.entity.vo;

import java.util.List;
import java.util.Map;

public record OrderItem(Integer eventId, Integer sessionId, Integer roomId, Map<Integer, List<String>> ticketsByChairs) {

}
