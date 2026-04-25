package com.fcs.events.entity;

import com.fcs.events.entity.vo.Validity;

import java.util.List;

public class Event {
    private Integer id;
    private String name;
    private Validity validity;
    private List<Session> sessions;

    public Event(final String name, final Validity validity, final List<Session> sessions) {
        this.name = name;
        this.validity = validity;
        this.sessions = sessions;
    }

    public boolean areValidSessionsDateRange() {
        for (Session s : this.sessions) {
            if (s.getStartDateTime().isAfter(this.validity.endDate()) ||
                    s.getStartDateTime().isBefore(this.validity.startDate())) {
                return false;
            }
        }
        return true;
    }

    public boolean isValid() {
        return name != null && !name.isEmpty()
                && validity != null && validity.startDate() != null && validity.endDate() != null
                && sessions != null && !sessions.isEmpty() && sessions.stream().allMatch(Session::isValid);
    }
}
