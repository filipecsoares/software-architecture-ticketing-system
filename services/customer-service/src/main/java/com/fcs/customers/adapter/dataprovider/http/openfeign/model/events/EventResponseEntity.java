package com.fcs.customers.adapter.dataprovider.http.openfeign.model.events;

import java.time.LocalDateTime;
import java.util.List;

public record EventResponseEntity(Integer id, String name, LocalDateTime startDate, LocalDateTime endDate, List<SessionResponseEntity> sessions) {

}
