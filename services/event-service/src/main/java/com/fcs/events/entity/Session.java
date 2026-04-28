package com.fcs.events.entity;

import java.time.LocalDateTime;
import java.util.Map;

public class Session {
    private Integer id;
    private String name;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Integer roomId;
    private Map<Integer, Integer> ticketTypeIdsByQtd;

    public Session(final String name, final LocalDateTime startDateTime, final LocalDateTime endDateTime, final Integer roomId, final Map<Integer, Integer> ticketTypeIdsByQtd) {
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.roomId = roomId;
        this.ticketTypeIdsByQtd = ticketTypeIdsByQtd;
    }

    public boolean isValid() {
        return name != null
                && !name.isEmpty()
                && startDateTime != null
                && endDateTime != null
                && roomId != null
                && ticketTypeIdsByQtd != null
                && !ticketTypeIdsByQtd.isEmpty();
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public Map<Integer, Integer> getTicketTypeIdsByQtd() {
        return ticketTypeIdsByQtd;
    }

    public String getName() {
        return name;
    }
}
