package com.fcs.events.usecase.model;

import java.time.LocalDateTime;
import java.util.List;

public record EventResponseModel(Integer id, String name, LocalDateTime startDate, LocalDateTime endDate, List<SessionResponseModel> sessions) {
}
