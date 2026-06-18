package com.fcs.admin.usecase.model;

import java.time.LocalDateTime;

public record AllocationModel(LocalDateTime startDateTime, LocalDateTime endDateTime) {
}
