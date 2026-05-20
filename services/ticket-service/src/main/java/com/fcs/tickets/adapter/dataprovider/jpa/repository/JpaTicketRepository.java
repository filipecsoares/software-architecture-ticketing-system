package com.fcs.tickets.adapter.dataprovider.jpa.repository;

import com.fcs.tickets.adapter.dataprovider.jpa.entity.JpaTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaTicketRepository extends JpaRepository<JpaTicket, Integer> {

    Optional<JpaTicket> findByName(String name);
}
