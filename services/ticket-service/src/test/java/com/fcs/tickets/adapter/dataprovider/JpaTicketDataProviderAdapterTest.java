package com.fcs.tickets.adapter.dataprovider;

import com.fcs.tickets.adapter.dataprovider.jpa.entity.JpaTicket;
import com.fcs.tickets.adapter.dataprovider.jpa.entity.JpaTicketType;
import com.fcs.tickets.adapter.dataprovider.jpa.repository.JpaTicketRepository;
import com.fcs.tickets.adapter.dataprovider.jpa.repository.JpaTicketTypeRepository;
import com.fcs.tickets.entity.Ticket;
import com.fcs.tickets.entity.vo.Currency;
import com.fcs.tickets.entity.vo.TicketType;
import com.fcs.tickets.entity.vo.TicketUnitPrice;
import com.fcs.tickets.usecase.model.TicketResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaTicketDataProviderAdapterTest {

    @Mock
    private JpaTicketRepository jpaTicketRepository;

    @Mock
    private JpaTicketTypeRepository jpaTicketTypeRepository;

    private JpaTicketDataProviderAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new JpaTicketDataProviderAdapter(jpaTicketRepository, jpaTicketTypeRepository);
    }

    // --- create ---

    @Test
    void create_shouldSaveTicketAndReturnId_whenTicketTypeAlreadyExists() {
        // Given
        Ticket ticket = new Ticket(null, "Show A", TicketType.INTEIRA, new TicketUnitPrice(50.0, Currency.REAL));

        JpaTicketType existingType = new JpaTicketType();
        existingType.setId(1);
        existingType.setName("INTEIRA");

        JpaTicket savedTicket = new JpaTicket();
        savedTicket.setId(10);

        when(jpaTicketTypeRepository.findByName("INTEIRA")).thenReturn(List.of(existingType));
        when(jpaTicketRepository.save(any(JpaTicket.class))).thenAnswer(inv -> {
            JpaTicket t = inv.getArgument(0);
            t.setId(10);
            return t;
        });

        // When
        Integer result = adapter.create(ticket);

        // Then
        assertEquals(10, result);
        verify(jpaTicketTypeRepository, never()).save(any());
        verify(jpaTicketRepository).save(any(JpaTicket.class));
    }

    @Test
    void create_shouldSaveNewTicketTypeAndTicket_whenTicketTypeDoesNotExist() {
        // Given
        Ticket ticket = new Ticket(null, "Show B", TicketType.MEIA, new TicketUnitPrice(25.0, Currency.DOLLAR));

        when(jpaTicketTypeRepository.findByName("MEIA")).thenReturn(List.of());
        when(jpaTicketRepository.save(any(JpaTicket.class))).thenAnswer(inv -> {
            JpaTicket t = inv.getArgument(0);
            t.setId(20);
            return t;
        });

        // When
        Integer result = adapter.create(ticket);

        // Then
        assertEquals(20, result);
        verify(jpaTicketTypeRepository).save(any(JpaTicketType.class));
        verify(jpaTicketRepository).save(any(JpaTicket.class));
    }

    // --- exists ---

    @Test
    void exists_shouldReturnTrue_whenTicketWithNameExists() {
        // Given
        String name = "Show A";
        JpaTicket jpaTicket = new JpaTicket();
        jpaTicket.setName(name);
        when(jpaTicketRepository.findByName(name)).thenReturn(Optional.of(jpaTicket));

        // When
        boolean result = adapter.exists(name);

        // Then
        assertTrue(result);
    }

    @Test
    void exists_shouldReturnFalse_whenTicketWithNameDoesNotExist() {
        // Given
        String name = "Nonexistent";
        when(jpaTicketRepository.findByName(name)).thenReturn(Optional.empty());

        // When
        boolean result = adapter.exists(name);

        // Then
        assertFalse(result);
    }

    // --- getAll ---

    @Test
    void getAll_shouldReturnAllTicketsMapped() {
        // Given
        JpaTicketType jpaTicketType = new JpaTicketType();
        jpaTicketType.setName("INTEIRA");

        JpaTicket jpaTicket = new JpaTicket();
        jpaTicket.setId(1);
        jpaTicket.setName("Show C");
        jpaTicket.setCurrency("REAL");
        jpaTicket.setUnitPrice("100.0");
        jpaTicket.setTicketType(jpaTicketType);

        when(jpaTicketRepository.findAll()).thenReturn(List.of(jpaTicket));

        // When
        List<TicketResponseModel> result = adapter.getAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).id());
        assertEquals("Show C", result.get(0).name());
    }

    @Test
    void getAll_shouldReturnEmptyList_whenNoTicketsExist() {
        // Given
        when(jpaTicketRepository.findAll()).thenReturn(List.of());

        // When
        List<TicketResponseModel> result = adapter.getAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // --- getById ---

    @Test
    void getById_shouldReturnTicketResponseModel_whenTicketExists() {
        // Given
        JpaTicketType jpaTicketType = new JpaTicketType();
        jpaTicketType.setName("PROMOCIONAL");

        JpaTicket jpaTicket = new JpaTicket();
        jpaTicket.setId(5);
        jpaTicket.setName("Show D");
        jpaTicket.setCurrency("EURO");
        jpaTicket.setUnitPrice("75.0");
        jpaTicket.setTicketType(jpaTicketType);

        when(jpaTicketRepository.findById(5)).thenReturn(Optional.of(jpaTicket));

        // When
        TicketResponseModel result = adapter.getById(5);

        // Then
        assertNotNull(result);
        assertEquals(5, result.id());
        assertEquals("Show D", result.name());
    }

    @Test
    void getById_shouldReturnNull_whenTicketDoesNotExist() {
        // Given
        when(jpaTicketRepository.findById(99)).thenReturn(Optional.empty());

        // When
        TicketResponseModel result = adapter.getById(99);

        // Then
        assertNull(result);
    }

    // --- delete ---

    @Test
    void delete_shouldDeleteTicket_whenTicketExists() {
        // Given
        JpaTicket jpaTicket = new JpaTicket();
        jpaTicket.setId(7);
        when(jpaTicketRepository.findById(7)).thenReturn(Optional.of(jpaTicket));

        // When
        adapter.delete(7);

        // Then
        verify(jpaTicketRepository).delete(jpaTicket);
    }

    @Test
    void delete_shouldDoNothing_whenTicketDoesNotExist() {
        // Given
        when(jpaTicketRepository.findById(99)).thenReturn(Optional.empty());

        // When
        adapter.delete(99);

        // Then
        verify(jpaTicketRepository, never()).delete(any());
    }
}