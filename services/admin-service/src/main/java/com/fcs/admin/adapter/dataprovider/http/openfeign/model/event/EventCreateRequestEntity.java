package com.fcs.admin.adapter.dataprovider.http.openfeign.model.event;

import java.time.LocalDateTime;
import java.util.List;

public record EventCreateRequestEntity(String name, LocalDateTime startDate, LocalDateTime endDate, List<SessionRequestEntity> sessions) {
}
