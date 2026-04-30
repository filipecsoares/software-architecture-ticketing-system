package com.fcs.events.adapter.dataprovider.http.openfeign.model.room;

import java.time.LocalDateTime;

public record AllocateRequestEntity(LocalDateTime startDateTime, LocalDateTime endDateTime) {
}
