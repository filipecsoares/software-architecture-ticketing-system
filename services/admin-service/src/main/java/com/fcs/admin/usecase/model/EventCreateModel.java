package com.fcs.admin.usecase.model;

import java.time.LocalDateTime;
import java.util.List;

public record EventCreateModel(String name, LocalDateTime startDate, LocalDateTime endDate, List<SessionModel> sessions) {
}
