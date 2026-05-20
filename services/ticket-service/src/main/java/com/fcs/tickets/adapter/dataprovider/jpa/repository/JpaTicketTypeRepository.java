package com.fcs.tickets.adapter.dataprovider.jpa.repository;

import com.fcs.tickets.adapter.dataprovider.jpa.entity.JpaTicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTicketTypeRepository extends JpaRepository<JpaTicketType, Integer> {

    List<JpaTicketType> findByName(String name);
}
