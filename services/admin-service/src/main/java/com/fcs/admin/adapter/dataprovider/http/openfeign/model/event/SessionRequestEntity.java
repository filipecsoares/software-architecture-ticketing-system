package com.fcs.admin.adapter.dataprovider.http.openfeign.model.event;

import java.time.LocalDateTime;
import java.util.Map;

public record SessionRequestEntity(String name, LocalDateTime startDateTime, LocalDateTime endDateTime, Integer roomId, Map<Integer, Integer> ticketTypeIdsByQtd) {

}
