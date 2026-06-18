package com.fcs.admin.usecase.model;

import java.time.LocalDateTime;
import java.util.Map;

public record SessionModel(String name, LocalDateTime startDateTime, LocalDateTime endDateTime, Integer roomId, Map<Integer, Integer> ticketTypeIdsByQtd) {

}
