package com.fcs.orders.adapter.dataprovider.jpa.repository;

import com.fcs.orders.adapter.dataprovider.jpa.entity.JpaOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaOrderItemRepository extends JpaRepository<JpaOrderItem, Integer> {

    Optional<List<JpaOrderItem>> findBySessionId(Integer sessionId);

    @Query(value = "SELECT * FROM ORDER_ITEMS oi " +
            "JOIN ORDER_ITEMS_TICKETS_BY_CHAIRS oitbc ON oi.id = oitbc.order_items_id " +
            "WHERE oi.session_id = :sessionId " +
            "AND oitbc.ticket_by_chair_id = :ticketId", nativeQuery = true)
    Optional<List<JpaOrderItem>> findBySessionIdAndTicketId(Integer sessionId, Integer ticketId);
}
