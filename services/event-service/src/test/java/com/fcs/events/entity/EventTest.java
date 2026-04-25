package com.fcs.events.entity;

import com.fcs.events.entity.vo.Validity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventTest {

    @Test
    public void shouldReturnFalseWhenEventIsNotValid() {
        // Given
        final LocalDateTime sessionOneLocalDateTime = LocalDateTime.of(2024, 2, 3, 0, 0, 0);
        final LocalDateTime sessionTwoLocalDateTime = LocalDateTime.of(2024, 2, 7, 0, 0, 0);
        /* Sessoes sem nome */
        final Session sessionOne = createSession("", sessionOneLocalDateTime, 1, Map.of(1, 10));
        final Session sessionTwo = createSession("", sessionTwoLocalDateTime, 2, Map.of(2, 5));
        final Event toCreate = createEvent(List.of(sessionOne, sessionTwo), new Validity(LocalDateTime.of(2024, 4, 1, 0, 0, 0), LocalDateTime.of(2024, 5, 1, 0, 0, 0)));
        // When
        final boolean isValid = toCreate.isValid();
        // Then
        assertFalse(isValid);
    }

    @Test
    public void shouldReturnFalseWhenEventHasSessionsOutOfValidityRange() {
        // Given
        final LocalDateTime sessionOneLocalDateTime = LocalDateTime.of(2024, 2, 3, 0, 0, 0);
        final LocalDateTime sessionTwoLocalDateTime = LocalDateTime.of(2024, 2, 7, 0, 0, 0);
        final Session sessionOne = createSession("Session One", sessionOneLocalDateTime, 1, Map.of(1, 10));
        final Session sessionTwo = createSession("Session Two", sessionTwoLocalDateTime, 2, Map.of(2, 5));
        final Event toCreate = createEvent(List.of(sessionOne, sessionTwo), new Validity(LocalDateTime.of(2024, 4, 1, 0, 0, 0), LocalDateTime.of(2024, 5, 1, 0, 0, 0)));
        // When
        final boolean isValid = toCreate.areValidSessionsDateRange();
        // Then
        assertFalse(isValid);
    }

    @Test
    public void shouldReturnTrueWhenEventIsValid() {
        // Given
        final LocalDateTime sessionOneLocalDateTime = LocalDateTime.of(2024, 4, 3, 0, 0, 0);
        final LocalDateTime sessionTwoLocalDateTime = LocalDateTime.of(2024, 4, 7, 0, 0, 0);
        final Session sessionOne = createSession("Session One", sessionOneLocalDateTime, 1, Map.of(1, 10));
        final Session sessionTwo = createSession("Session Two", sessionTwoLocalDateTime, 2, Map.of(2, 5));
        final Event toCreate = createEvent(List.of(sessionOne, sessionTwo), new Validity(LocalDateTime.of(2024, 4, 1, 0, 0, 0), LocalDateTime.of(2024, 5, 1, 0, 0, 0)));
        // When
        final boolean isValid = toCreate.areValidSessionsDateRange();
        // Then
        assertTrue(isValid);
    }

    private Session createSession(String name, LocalDateTime startDateTime, Integer roomId, Map<Integer, Integer> ticketTypeIdsByQtd) {
        return new Session(name, startDateTime, startDateTime.plusHours(2), roomId, ticketTypeIdsByQtd);
    }

    private Event createEvent(List<Session> sessions, Validity validity) {
        return new Event("", validity, sessions);
    }
}