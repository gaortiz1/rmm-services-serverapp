package com.example.rmmservices.repositories;

import com.example.rmmservices.models.CustomerService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerServiceRepository extends JpaRepository<CustomerService, Long> {

    Optional<CustomerService> findByServiceIdAndCustomerId(Long serviceId, Long customerId);

    List<CustomerService> findByCustomerId(Long id);
}
