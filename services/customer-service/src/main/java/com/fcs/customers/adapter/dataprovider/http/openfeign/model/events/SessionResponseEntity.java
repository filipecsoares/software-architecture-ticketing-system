package com.fcs.customers.adapter.dataprovider.http.openfeign.model.events;

import java.time.LocalDateTime;
import java.util.Map;

public record SessionResponseEntity(Integer id, String name, LocalDateTime dateTime, Integer roomId, Map<Integer, Integer> ticketTypeIdsByQtd) {
}
