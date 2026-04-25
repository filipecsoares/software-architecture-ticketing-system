package com.fcs.events.entity.vo;

import java.time.LocalDateTime;

public record Validity(LocalDateTime startDate, LocalDateTime endDate) {
}
