package com.fcs.events.usecase.model;

import java.time.LocalDateTime;
import java.util.Map;

public record SessionResponseModel(Integer id, String name, LocalDateTime dateTime, Integer roomId, Map<Integer, Integer> ticketTypeIdsByQtd) {
}
