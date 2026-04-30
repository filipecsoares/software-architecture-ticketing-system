package com.fcs.events.adapter.dataprovider.http.openfeign.model.room;

import java.time.LocalDateTime;

public class AllocationResponseEntity {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}
