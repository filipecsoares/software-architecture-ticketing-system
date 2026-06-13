package com.fcs.orders.adapter.dataprovider.jpa.repository;

import com.fcs.orders.adapter.dataprovider.jpa.entity.JpaReservedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaReservedOrderRepository extends JpaRepository<JpaReservedOrder, Integer> {

    Optional<List<JpaReservedOrder>> findBySessionIdAndCreationDateTimeAfter(Integer sessionId, LocalDateTime creationDateTime);

    Optional<JpaReservedOrder> findByCustomerIdAndCreationDateTimeAfter(Integer customerId, LocalDateTime creationDateTime);

    @Query(value = "SELECT * FROM RESERVED_ORDERS ro " +
            "JOIN RESERVED_ORDERS_TICKETS_BY_CHAIRS rtc ON ro.id = rtc.reserved_orders_id " +
            "WHERE ro.session_id = :sessionId " +
            "AND rtc.ticket_by_chair_id = :ticketId " +
            "AND ro.creation_date_time > :timeLimit", nativeQuery = true)
    Optional<List<JpaReservedOrder>> findBySessionIdAndTicketIdAndCreationDateTimeAfter(@Param("sessionId") Integer sessionId,
                                                                                        @Param("ticketId") Integer ticketId,
                                                                                        @Param("timeLimit") LocalDateTime timeLimit);

    Optional<JpaReservedOrder> findByIdAndCreationDateTimeAfter(Integer id, LocalDateTime creationDateTime);
}
