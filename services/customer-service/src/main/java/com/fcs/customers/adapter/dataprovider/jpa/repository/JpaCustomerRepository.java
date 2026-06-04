package com.fcs.customers.adapter.dataprovider.jpa.repository;

import com.fcs.customers.adapter.dataprovider.jpa.entity.JpaCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaCustomerRepository extends JpaRepository<JpaCustomer, Integer> {

    Optional<JpaCustomer> findByDocument(String document);
}
