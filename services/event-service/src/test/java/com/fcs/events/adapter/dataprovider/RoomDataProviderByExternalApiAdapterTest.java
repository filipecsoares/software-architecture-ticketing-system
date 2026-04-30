package com.fcs.events.adapter.dataprovider;

import com.fcs.events.adapter.dataprovider.http.openfeign.RoomFeignClient;
import com.fcs.events.adapter.dataprovider.http.openfeign.model.room.AllocationResponseEntity;
import com.fcs.events.adapter.dataprovider.http.openfeign.model.room.RoomResponseEntity;
import com.fcs.events.adapter.dataprovider.http.openfeign.model.room.SeatResponseEntity;
import com.fcs.events.usecase.model.RoomModel;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomDataProviderByExternalApiAdapterTest {

    @Mock
    private RoomFeignClient roomFeignClient;

    @InjectMocks
    private RoomDataProviderByExternalApiAdapter adapter;

    @Test
    void shouldFindByWhenRoomExistsAndIsAvailable() {
        // Arrange
        final Integer roomId = 1;
        final LocalDateTime requestDateTime = LocalDateTime.of(2026, 4, 30, 10, 0);

        RoomResponseEntity roomResponse = mock(RoomResponseEntity.class);
        when(roomResponse.getName()).thenReturn("Conference Room A");
        when(roomResponse.getSeats()).thenReturn(List.of(new SeatResponseEntity()));
        when(roomResponse.getAllocations()).thenReturn(List.of());

        when(roomFeignClient.getRoom(roomId)).thenReturn(roomResponse);

        // Act
        RoomModel result = adapter.findBy(roomId, requestDateTime);

        // Assert
        assertNotNull(result);
        assertEquals("Conference Room A", result.name());
        assertEquals(1, result.totalSeats());
        assertTrue(result.exists());
        assertTrue(result.isAvailable());
        verify(roomFeignClient, times(1)).getRoom(roomId);
    }

    @Test
    void shouldNotFindAvailableWhenRoomExistsButHasTimeConflict() {
        // Arrange
        final Integer roomId = 1;
        final LocalDateTime requestDateTime = LocalDateTime.of(2026, 4, 30, 10, 0);
        final LocalDateTime allocationStart = LocalDateTime.of(2026, 4, 30, 9, 0);
        final LocalDateTime allocationEnd = LocalDateTime.of(2026, 4, 30, 11, 0);

        AllocationResponseEntity allocation = mock(AllocationResponseEntity.class);
        when(allocation.getStartDateTime()).thenReturn(allocationStart);
        when(allocation.getEndDateTime()).thenReturn(allocationEnd);

        RoomResponseEntity roomResponse = mock(RoomResponseEntity.class);
        when(roomResponse.getName()).thenReturn("Conference Room B");
        when(roomResponse.getSeats()).thenReturn(List.of(new SeatResponseEntity()));
        when(roomResponse.getAllocations()).thenReturn(List.of(allocation));

        when(roomFeignClient.getRoom(roomId)).thenReturn(roomResponse);

        // Act
        RoomModel result = adapter.findBy(roomId, requestDateTime);

        // Assert
        assertNotNull(result);
        assertEquals("Conference Room B", result.name());
        assertEquals(1, result.totalSeats());
        assertTrue(result.exists());
        assertFalse(result.isAvailable());
        verify(roomFeignClient, times(1)).getRoom(roomId);
    }

    @Test
    void shouldReturnNonExistentRoomWhenRoomDoesNotExist() {
        // Arrange
        final Integer roomId = 999;
        final LocalDateTime requestDateTime = LocalDateTime.of(2026, 4, 30, 10, 0);

        when(roomFeignClient.getRoom(roomId)).thenReturn(null);

        // Act
        RoomModel result = adapter.findBy(roomId, requestDateTime);

        // Assert
        assertNotNull(result);
        assertEquals("", result.name());
        assertEquals(0, result.totalSeats());
        assertFalse(result.exists());
        assertFalse(result.isAvailable());
        verify(roomFeignClient, times(1)).getRoom(roomId);
    }

    @Test
    void shouldAllocateSuccessfully() {
        // Arrange
        final Integer roomId = 1;
        final LocalDateTime startDateTime = LocalDateTime.of(2026, 4, 30, 10, 0);
        final LocalDateTime endDateTime = LocalDateTime.of(2026, 4, 30, 11, 0);

        doNothing().when(roomFeignClient).allocate(eq(roomId), any());

        // Act
        boolean result = adapter.allocate(roomId, startDateTime, endDateTime);

        // Assert
        assertTrue(result);
        verify(roomFeignClient, times(1)).allocate(eq(roomId), any());
    }

    @Test
    void shouldNotAllocateWhenFeignExceptionOccurs() {
        // Arrange
        final Integer roomId = 1;
        final LocalDateTime startDateTime = LocalDateTime.of(2026, 4, 30, 10, 0);
        final LocalDateTime endDateTime = LocalDateTime.of(2026, 4, 30, 11, 0);

        doThrow(FeignException.class).when(roomFeignClient).allocate(eq(roomId), any());

        // Act
        boolean result = adapter.allocate(roomId, startDateTime, endDateTime);

        // Assert
        assertFalse(result);
        verify(roomFeignClient, times(1)).allocate(eq(roomId), any());
    }

}