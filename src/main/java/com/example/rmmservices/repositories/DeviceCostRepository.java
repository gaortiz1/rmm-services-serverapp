package com.example.rmmservices.repositories;

import com.example.rmmservices.models.DeviceCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceCostRepository extends JpaRepository<DeviceCost, Long> {
}
