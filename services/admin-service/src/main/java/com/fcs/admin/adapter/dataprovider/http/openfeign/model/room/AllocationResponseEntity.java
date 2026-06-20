package com.fcs.admin.adapter.dataprovider.http.openfeign.model.room;

import java.time.LocalDateTime;

public record AllocationResponseEntity(Integer id, LocalDateTime startDateTime, LocalDateTime endDateTime) {
}
