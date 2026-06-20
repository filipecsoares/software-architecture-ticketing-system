package com.fcs.admin.adapter.dataprovider.http.openfeign.model.event;

import com.fcs.admin.usecase.model.SessionResponseModel;

import java.time.LocalDateTime;
import java.util.List;

public record EventResponseEntity(Integer id, String name, LocalDateTime startDate, LocalDateTime endDate, List<SessionResponseModel> sessions) {
}
